package com.afj.solution.buyitapp.integration.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.integration.BaseTest;
import com.afj.solution.buyitapp.model.enums.Currency;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.payload.request.LoginRequest;
import com.afj.solution.buyitapp.payload.response.JwtResponse;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayName("Product controller tests")
class ProductTest extends BaseTest {

    private String token;

    @BeforeEach
    void login() {
        final LoginRequest loginRequest = new LoginRequest("integration_test", "Test123$");
        final HttpEntity<LoginRequest> loginRequestHttpEntity = new HttpEntity<>(loginRequest, new HttpHeaders());

        final ResponseEntity<JwtResponse> response = restTemplate.exchange(
                createUrlWithPort(LOGIN_PATH),
                HttpMethod.POST, loginRequestHttpEntity, JwtResponse.class);

        token = response.getBody().getToken();
    }

    @Test
    @DisplayName("Create Product using POST")
    void createProduct() {
        final HttpHeaders headers = new HttpHeaders();

        headers.add(AUTHORIZATION, format("Bearer %s", token));
        final CreateProductRequest createProductRequest = new CreateProductRequest(
                "Citroen",
                22_000,
                1,
                Currency.USD,
                "Car for taxi",
                categoryId,
                subCategoryId);
        final HttpEntity<CreateProductRequest> createProductRequestHttpEntity = new HttpEntity<>(createProductRequest, headers);

        final ResponseEntity<Response> response = restTemplate.exchange(
                createUrlWithPort(PRODUCTS_PATH),
                HttpMethod.POST, createProductRequestHttpEntity, Response.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(201);

        assertThat(response.getBody().getResponse())
                .isEqualTo("Success");
    }

    @Test
    @DisplayName("Create Product using POST - Without category")
    void createProductWithoutCategory() {
        final HttpHeaders headers = new HttpHeaders();

        headers.add(AUTHORIZATION, format("Bearer %s", token));
        final CreateProductRequest createProductRequest = new CreateProductRequest(
                "Citroen",
                22_000,
                1,
                Currency.USD,
                "Car for taxi",
                null,
                subCategoryId);
        final HttpEntity<CreateProductRequest> createProductRequestHttpEntity = new HttpEntity<>(createProductRequest, headers);

        final ResponseEntity<Response> response = restTemplate.exchange(
                createUrlWithPort(PRODUCTS_PATH),
                HttpMethod.POST, createProductRequestHttpEntity, Response.class);

        final Response<String> errorResponse = response.getBody();
        final String message = errorResponse.getError().stream().findFirst().get().getMessage();
        assertThat(response.getStatusCodeValue())
                .isEqualTo(400);
        assertThat(response.getBody().getResponse())
                .isEqualTo("Failed");
        assertThat(message)
                .isEqualTo("categoryId must not be null");
    }

    @Test
    @DisplayName("Create Product using POST - Without subcategory")
    void createProductWithoutSubcategory() {
        final HttpHeaders headers = new HttpHeaders();

        headers.add(AUTHORIZATION, format("Bearer %s", token));
        final CreateProductRequest createProductRequest = new CreateProductRequest(
                "Citroen",
                22_000,
                1,
                Currency.USD,
                "Car for taxi",
                categoryId,
                null);
        final HttpEntity<CreateProductRequest> createProductRequestHttpEntity = new HttpEntity<>(createProductRequest, headers);

        final ResponseEntity<Response> response = restTemplate.exchange(
                createUrlWithPort(PRODUCTS_PATH),
                HttpMethod.POST, createProductRequestHttpEntity, Response.class);

        final Response<String> errorResponse = response.getBody();
        final String message = errorResponse.getError().stream().findFirst().get().getMessage();
        assertThat(response.getStatusCodeValue())
                .isEqualTo(400);
        assertThat(response.getBody().getResponse())
                .isEqualTo("Failed");
        assertThat(message)
                .isEqualTo("subCategoryId must not be null");
    }
}
