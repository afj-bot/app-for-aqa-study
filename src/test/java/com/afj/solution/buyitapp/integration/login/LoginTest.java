package com.afj.solution.buyitapp.integration.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.afj.solution.buyitapp.integration.BaseTest;
import com.afj.solution.buyitapp.payload.request.LoginRequest;

import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@DisplayName("Login controller tests")
class LoginTest extends BaseTest {
    private static final String LOGIN_ENDPOINT = "/api/v1/login";
    private static final String ANONYMOUS_AUTH_ENDPOINT = "/api/v1/auth/anonymous";

    @Test
    @DisplayName("Login with POST")
    void login() {
        final HttpHeaders headers = new HttpHeaders();
        final LoginRequest request = new LoginRequest("integration_test", "Test123$");
        final HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);

        final ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort(LOGIN_ENDPOINT),
                HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(200);

        assertThat(response.getBody())
                .contains("token");
    }

    @Test
    @DisplayName("Anonymous authentication GET method")
    void authAnonymous() {
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<Object> entity = new HttpEntity<>(headers);

        final ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort(ANONYMOUS_AUTH_ENDPOINT),
                HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(200);

        final String cookie = response.getHeaders().get(SET_COOKIE).get(0);
        assertThat(cookie)
                .contains("anonymous");
    }

    @Test
    @DisplayName("Anonymous authentication POST method")
    void anonymousToken() {
        final HttpEntity<Object> entity = new HttpEntity<>(new HttpHeaders());

        final ResponseEntity<String> cookieResponse = restTemplate.exchange(
                createUrlWithPort(ANONYMOUS_AUTH_ENDPOINT),
                HttpMethod.GET, entity, String.class);
        final String cookie = cookieResponse.getHeaders().get(SET_COOKIE).get(0);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(COOKIE, cookie);
        final HttpEntity<Object> cookieEntity = new HttpEntity<>(headers);
        final ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort(ANONYMOUS_AUTH_ENDPOINT),
                HttpMethod.POST, cookieEntity, String.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(200);

        assertThat(response.getBody())
                .contains("token");
    }
}
