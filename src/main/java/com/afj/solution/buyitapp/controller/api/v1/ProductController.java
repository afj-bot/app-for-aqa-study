package com.afj.solution.buyitapp.controller.api.v1;

import java.io.IOException;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.payload.request.UpdateCharacteristicRequest;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.product.ProductServiceImp;

import static com.afj.solution.buyitapp.constans.Patterns.generateSuccessResponse;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/products", produces = "application/json; charset=utf-8")
public class ProductController {

    private final ProductServiceImp productService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ProductController(final ProductServiceImp productService,
                             final JwtTokenProvider jwtTokenProvider) {
        this.productService = productService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiOperation(value = "Get products", notes = "All Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Products returned successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping
    public @ResponseBody
    Page<ProductResponse> getProducts(final Pageable pageable) {
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Get products request for id -> {}", userId);
        log.info("Get products by {}", pageable);
        return productService.getProducts(pageable);
    }

    @ApiOperation(value = "Get product image", notes = "All Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Image successfully find"),
            @ApiResponse(code = 404, message = "Image not found"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping(value = "/{id}/image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImage(@Valid @NotEmpty @PathVariable final UUID id) {
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Get an image request for id -> {}", userId);
        log.info("Get an image for product {}", id);
        return productService.getImageByProductId(id);
    }

    @ApiOperation(value = "Create a new product", notes = "User, Admin Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product created successfully"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public @ResponseBody
    Response<String> createProduct(@Valid @RequestBody final CreateProductRequest createProductRequest) {
        final String username = jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.getToken().substring(7));
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive create request from username -> {}", username);
        productService.save(createProductRequest, userId);
        return generateSuccessResponse();
    }

    @ApiOperation(value = "Update the product quantity", notes = "User, Admin Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product quantity updated successfully"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PutMapping("/{id}/quantity")
    public @ResponseBody
    Response<String> updateProductQuantity(@Valid @NotEmpty @PathVariable final UUID id,
                                           @RequestParam("count") final int count) {
        final String username = jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive update quantity of product {} request from username -> {}", id, username);
        productService.increaseProductQuantity(id, count);
        return generateSuccessResponse();
    }

    @ApiOperation(value = "Update the product characteristic", notes = "User, Admin Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product characteristic updated successfully"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PutMapping("/{id}/characteristic")
    public @ResponseBody
    Response<String> updateProductCharacteristic(@Valid @NotEmpty @PathVariable final UUID id,
                                                 @RequestBody final UpdateCharacteristicRequest updateCharacteristicRequest) {
        final String username = jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive update characteristic of product {} request from username -> {}", id, username);
        productService.updateCharacteristicToProduct(id, updateCharacteristicRequest);
        return generateSuccessResponse();
    }


    @ApiOperation(value = "Add image to the product", notes = "User, Admin Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Image added successfully to product"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{id}/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ProductResponse addImageToProduct(@Valid @NotEmpty @PathVariable final UUID id,
                                      @RequestPart("file") final MultipartFile file) throws IOException {
        final String username = jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Upload an image for product {} by username -> {}", id, username);
        return productService.addImageToProduct(id, file);
    }

    @ApiOperation(value = "Get My products", notes = "User, Admin Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Products returned successfully"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping("/me")
    public @ResponseBody
    Page<ProductResponse> getMyProducts(final Pageable pageable,
                                        @RequestParam(value = "title", required = false) final String title,
                                        @RequestParam(value = "description", required = false) final String description) {
        final UUID userId = jwtTokenProvider.getUuidFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Get products request for id -> {}", userId);
        log.info("Get products by {}", pageable);
        return productService.getMyProducts(pageable, userId, title, description);
    }
}
