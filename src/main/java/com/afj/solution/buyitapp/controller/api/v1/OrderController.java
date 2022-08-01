package com.afj.solution.buyitapp.controller.api.v1;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;
import com.afj.solution.buyitapp.payload.response.OrderResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.order.OrderServiceImpl;

import static com.afj.solution.buyitapp.constans.Patterns.generateSuccessResponse;

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

    @ApiOperation(value = "Create a new order", notes = "Anonymous, User Role", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order created successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    OrderResponse createOrder(@Valid @RequestBody final CreateOrderRequest createOrderRequest) {
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive create order request for id -> {}", userId);
        return orderService.create(createOrderRequest, userId);
    }

    @ApiOperation(value = "Cancel order", notes = "Anonymous, User Role", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order created successfully"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PutMapping("/{id}/cancel")
    public @ResponseBody
    Response<String> cancelOrder(@Valid @NotEmpty @PathVariable final UUID id) {
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive cancel order request for id -> {}", userId);
        orderService.cancelOrder(id);
        return generateSuccessResponse();
    }
}
