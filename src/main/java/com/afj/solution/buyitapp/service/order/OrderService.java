package com.afj.solution.buyitapp.service.order;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.afj.solution.buyitapp.model.Order;
import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;
import com.afj.solution.buyitapp.payload.response.OrderResponse;

/**
 * @author Tomash Gombosh
 */
public interface OrderService {

    OrderResponse create(CreateOrderRequest createOrderRequest, UUID userId);

    void cancelOrder(UUID orderId);

    Page<OrderResponse> getMyOrders(Pageable pageable, UUID userId);

    Order findById(UUID orderId);
}
