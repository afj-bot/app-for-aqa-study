package com.afj.solution.buyitapp.unit.service.order;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;
import com.afj.solution.buyitapp.payload.response.OrderResponse;
import com.afj.solution.buyitapp.service.order.OrderServiceImpl;
import com.afj.solution.buyitapp.unit.BaseTest;

import static com.afj.solution.buyitapp.model.enums.OrderStatus.CANCEL;

/**
 * @author Tomash Gombosh
 */
@DisplayName("Order service tests")
class OrderServiceTest extends BaseTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Create Order")
    void createOrder() {
        final CreateOrderRequest request = new CreateOrderRequest(List.of(productId));

        final OrderResponse orderResponse = orderService.create(request, userId);
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse.getProducts()).anyMatch(p -> p.getId().equals(productId));

    }

    @Test
    @DisplayName("Get my Orders")
    void getMyOrders() {
        final Page<OrderResponse> ordersResponse = orderService.getMyOrders(Pageable.unpaged(), userId);
        assertThat(ordersResponse)
                .anyMatch(r -> r.getProducts().stream().anyMatch(p -> p.getId().equals(productId)));
    }

    @Test
    @DisplayName("Cancel order with status CANCEL")
    void cancelOrderWithCancelStatus() {
        assertThatThrownBy(() -> orderService.cancelOrder(cancelOrderId))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Cancel order with status IN PROGRESS")
    void cancelOrderWithInProgressStatus() {
        assertThatThrownBy(() -> orderService.cancelOrder(inProgressOrderId))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @Rollback
    @DisplayName("Cancel order with status PENDING")
    void cancelOrderWithPendingStatus() {
        orderService.cancelOrder(waitingForPaymentOrderId);
        assertThat(orderService.findById(waitingForPaymentOrderId).getStatus())
                .isEqualTo(CANCEL);
    }
}
