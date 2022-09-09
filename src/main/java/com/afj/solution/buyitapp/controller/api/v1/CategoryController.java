package com.afj.solution.buyitapp.controller.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.repository.CategoryRepository;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/category", produces = "application/json; charset=utf-8")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public @ResponseBody
    Page<Category> getMyOrders(final Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
