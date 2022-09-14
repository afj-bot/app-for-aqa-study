package com.afj.solution.buyitapp.controller.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.payload.response.CategoryResponse;
import com.afj.solution.buyitapp.service.category.CategoryService;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/category", produces = "application/json; charset=utf-8")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @ApiOperation(value = "Get all categories", notes = "All Roles", authorizations = {@Authorization("Bearer")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Category received successfully"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping

    public @ResponseBody
    Page<CategoryResponse> getMyOrders(final Pageable pageable, @RequestHeader("Accept-Language") final String language) {
        log.info("Get all products for {} and language {}", pageable, language);
        return categoryService.getCategories(pageable, language);
    }
}
