package com.afj.solution.buyitapp.service.order;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.Order;
import com.afj.solution.buyitapp.model.product.Product;
import com.afj.solution.buyitapp.model.enums.OrderStatus;
import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;
import com.afj.solution.buyitapp.payload.response.OrderResponse;
import com.afj.solution.buyitapp.repository.OrderRepository;
import com.afj.solution.buyitapp.service.converters.order.OrderToResponseConverter;
import com.afj.solution.buyitapp.service.localize.TranslatorService;
import com.afj.solution.buyitapp.service.product.ProductService;

import static com.afj.solution.buyitapp.model.enums.OrderStatus.CANCEL;
import static java.lang.String.format;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderToResponseConverter converter;
    private final ProductService productService;
    private final TranslatorService translator;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository,
                            final OrderToResponseConverter converter,
                            final ProductService productService,
                            final TranslatorService translator) {
        this.orderRepository = orderRepository;
        this.converter = converter;
        this.productService = productService;
        this.translator = translator;
    }

    @Override
    public OrderResponse create(final CreateOrderRequest createOrderRequest,
                                final UUID userId) {
        log.info("Create order for products {}", createOrderRequest.getProductIds());
        final List<Product> outOfStockProducts = productService
                .productsWithEmptyQuantity(createOrderRequest.getProductIds());
        if (!outOfStockProducts.isEmpty()) {
            log.info("The products is out of the quantity {}", outOfStockProducts);
            throw new BadRequestException(format(translator.toLocale("error.product.out-of-stock"),
                    outOfStockProducts.stream().map(Product::getName).collect(Collectors.toList())));
        }
        final Set<Product> products = productService.getProductsById(createOrderRequest.getProductIds());
        log.info("Get products from database {}", products);

        final double total = products.stream().mapToDouble(Product::getPrice).sum();
        log.info("Count total price {}", total);

        final Order order = orderRepository.save(new Order(o -> {
            o.setProductIds(new HashSet<>(createOrderRequest.getProductIds()));
            o.setUserId(userId);
            o.setStatus(OrderStatus.PENDING);
            o.setTotal((float) total);
        }));
        log.info("Save order to the database {}", order);

        products.forEach(p -> productService.decreaseProductQuantity(p.getId(), 1));
        final OrderResponse orderResponse = converter.convert(order);
        orderResponse.setProducts(products);
        return orderResponse;
    }

    @Override
    public Page<OrderResponse> getMyOrders(final Pageable pageable, final UUID userId) {
        return orderRepository
                .findAllByUserId(pageable, userId)
                .map(o -> {
                    final OrderResponse response = converter.convert(o);
                    response.setProducts(productService.getProductsById(o.getProductIds().stream().toList()));
                    return response;
                });
    }

    @Override
    public Order findById(final UUID orderId) {
        log.info("Find the order {}", orderId);
        return this.orderRepository
                .findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        format(translator.toLocale("error.order.not-found"), orderId))
                );
    }

    @Override
    @SuppressWarnings("PMD")
    public void cancelOrder(final UUID orderId) {
        final Optional<Order> order = this.orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new EntityNotFoundException(format(translator.toLocale("error.order.not-found"), orderId));
        }
        switch (order.get().getStatus()) {
            case CANCEL -> throw new BadRequestException(translator.toLocale("error.order.cancel"));
            case PENDING, WAITING_FOR_PAYMENT -> {
                final Order cancelOrder = order.get();
                cancelOrder.setStatus(CANCEL);
                orderRepository.save(cancelOrder);
                log.info("Order {} was successfully canceled", cancelOrder.getId());
                cancelOrder
                        .getProductIds()
                        .forEach(id -> productService.increaseProductQuantity(id, 1));
            }
            default -> throw new BadRequestException(translator.toLocale("error.order.unsupported-state"));
        }
    }
}
