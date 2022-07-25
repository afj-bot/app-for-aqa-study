package com.afj.solution.buyitapp.controller.api.v1;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.product.ProductServiceImp;

import static com.afj.solution.buyitapp.common.Patterns.generateSuccessResponse;

/**
 * @author Tomash Gombosh
 */
@RestController
@RequestMapping(path = "/api/v1/products", produces = "application/json; charset=utf-8")
@Slf4j
public class ProductController {

    private final ProductServiceImp productService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ProductController(final ProductServiceImp productService,
                             final JwtTokenProvider jwtTokenProvider) {
        this.productService = productService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation("Get products")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Products returned successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping
    public @ResponseBody
    Page<ProductResponse> getProducts(final Pageable pageable) {
        log.info("Get products by {}", pageable);
        return productService.getProducts(pageable);
    }

    @ApiOperation(value = "Create a new product", notes = "ROLE_ADMIN", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product created successfully"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Response<String> createProduct(@Valid final CreateProductRequest request) {
        final String username = jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive create request from username -> {}", username);
        productService.save(request);
        return generateSuccessResponse();
    }
}
