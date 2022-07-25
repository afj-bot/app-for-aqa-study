package com.afj.solution.buyitapp.controller.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.service.product.ProductServiceImp;

/**
 * @author Tomash Gombosh
 */
@RestController
@RequestMapping(path = "/api/v1/products", produces = "application/json; charset=utf-8")
@Slf4j
public class ProductController {

    private final ProductServiceImp productService;

    @Autowired
    public ProductController(final ProductServiceImp productService) {
        this.productService = productService;
    }

    @ApiOperation("Login user into the application")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login was successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping
    public @ResponseBody
    Page<ProductResponse> getProducts(final Pageable pageable) {
        log.info("Get products by {}", pageable);
        return productService.getProducts(pageable);
    }
}
