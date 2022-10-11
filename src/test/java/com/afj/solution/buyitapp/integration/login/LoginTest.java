package com.afj.solution.buyitapp.integration.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.afj.solution.buyitapp.integration.BaseTest;
import com.afj.solution.buyitapp.payload.request.LoginRequest;

@DisplayName("Login controller tests")
class LoginTest extends BaseTest {

    @Test
    @DisplayName("Login")
    void login() {
        final HttpHeaders headers = new HttpHeaders();
        final LoginRequest request = new LoginRequest("integration_test", "Test123$");
        final HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);

        final ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort("/api/v1/login"),
                HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(200);

        assertThat(response.getBody())
                .contains("token");
    }
}
