package com.afj.solution.buyitapp.controller.api.v1;

import java.util.UUID;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;
import com.afj.solution.buyitapp.payload.response.OrderResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.order.OrderServiceImpl;

/**
 * @author Tomash Gombosh
 */
@RestController
@RequestMapping(path = "/api/v1/orders", produces = "application/json; charset=utf-8")
@Slf4j
public class OrderController {

    private final OrderServiceImpl orderService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OrderController(final OrderServiceImpl orderService,
                           final JwtTokenProvider jwtTokenProvider) {
        this.orderService = orderService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation("Create a new order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order created successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    OrderResponse createOrder(@Valid final CreateOrderRequest request) {
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive create order request for id -> {}", userId);
        return orderService.create(request, userId);
    }

}
