package com.afj.solution.buyitapp.service.converters.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.Order;
import com.afj.solution.buyitapp.payload.response.OrderResponse;
import com.afj.solution.buyitapp.service.converters.Converter;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class OrderToResponseConverter implements Converter<Order, OrderResponse> {
    @Override
    public OrderResponse convert(final Order order) {
        log.info("Convert the order({}) to response", order);
        return new OrderResponse(orderResponse -> {
            orderResponse.setId(order.getId());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setTotal(order.getTotal());
            orderResponse.setCreatedAt(order.getCreatedAt());
            orderResponse.setUpdatedAt(order.getUpdatedAt());
        });
    }
}
