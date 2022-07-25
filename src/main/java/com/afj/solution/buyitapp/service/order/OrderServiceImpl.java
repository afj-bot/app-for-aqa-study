package com.afj.solution.buyitapp.service.order;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.model.Order;
import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.model.enums.OrderStatus;
import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;
import com.afj.solution.buyitapp.payload.response.OrderResponse;
import com.afj.solution.buyitapp.repository.OrderRepository;
import com.afj.solution.buyitapp.service.converters.OrderToResponseConverter;
import com.afj.solution.buyitapp.service.product.ProductService;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderToResponseConverter converter;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository,
                            final OrderToResponseConverter converter,
                            final ProductService productService) {
        this.orderRepository = orderRepository;
        this.converter = converter;
        this.productService = productService;
    }

    @Override
    public OrderResponse create(final CreateOrderRequest createOrderRequest,
                                final UUID userId) {

        log.info("Create order for products {}", createOrderRequest.getProductIds());
        final Set<Product> products = createOrderRequest
                .getProductIds()
                .stream()
                .map(productService::findById)
                .collect(Collectors.toSet());
        log.info("Get products from database {}", products);
        final double total = products.stream().mapToDouble(Product::getPrice).sum();
        log.info("Count total price {}", total);
        final Order order = orderRepository.save(new Order(o -> {
            o.setProductIds(createOrderRequest.getProductIds());
            o.setUserId(userId);
            o.setStatus(OrderStatus.PENDING);
            o.setTotal((float) total);
        }));
        log.info("Save order to the database {}", order);
        final OrderResponse orderResponse = converter.convert(order);
        orderResponse.setProducts(products);
        return orderResponse;
    }
}
