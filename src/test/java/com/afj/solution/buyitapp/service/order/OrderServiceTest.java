package com.afj.solution.buyitapp.service.order;

import java.util.UUID;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afj.solution.buyitapp.payload.request.CreateOrderRequest;

/**
 * @author Tomash Gombosh
 */
@DisplayName("Order Service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest implements WithAssertions {

    @Test
    @DisplayName("Create Order")
    void createOrder() {
        final UUID userId = UUID.randomUUID();
        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();
    }
}
