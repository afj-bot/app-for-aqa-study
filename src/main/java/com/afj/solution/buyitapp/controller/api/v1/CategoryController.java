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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.payload.request.CreateCategoryRequest;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;
import com.afj.solution.buyitapp.security.JwtTokenProvider;
import com.afj.solution.buyitapp.service.category.CategoryService;

import static com.afj.solution.buyitapp.constans.Patterns.generateSuccessResponse;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/category", produces = "application/json; charset=utf-8")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "Get all categories", notes = "All Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Category received successfully"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping
    @PreAuthorize("hasRole('ANONYMOUS') or hasRole('USER') or hasRole('ADMIN')")
    public @ResponseBody
    Page<CategoryResponse> getCategories(final Pageable pageable,
                                       @RequestHeader(value = "Accept-Language", defaultValue = "gb")
                                       final String language) {
        log.info("Get all products for {} and language {}", pageable, language);
        return categoryService.getCategories(pageable, language);
    }

    @ApiOperation(value = "Create a new category", notes = "Admin Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Category created successfully"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Response<String> createCategory(@Valid @RequestBody final CreateCategoryRequest createCategoryRequest,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "gb") final String language) {
        final String username = jwtTokenProvider.getUsernameFromToken(jwtTokenProvider.getToken().substring(7));
        log.info("Receive create category request from username -> {}", username);
        categoryService.save(createCategoryRequest, language);
        return generateSuccessResponse();
    }
}
