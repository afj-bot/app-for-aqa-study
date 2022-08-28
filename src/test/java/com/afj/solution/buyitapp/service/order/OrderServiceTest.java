package com.afj.solution.buyitapp.service.order;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Tomash Gombosh
 */
@DisplayName("Order Service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest implements WithAssertions {

    @Test
    @DisplayName("Create Order")
    void createOrder() {
        assertThat(true).isTrue();
    }
}
