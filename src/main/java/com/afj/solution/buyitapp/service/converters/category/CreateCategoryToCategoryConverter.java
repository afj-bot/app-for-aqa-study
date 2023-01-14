package com.afj.solution.buyitapp.service.converters.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.payload.request.CreateCategoryRequest;
import com.afj.solution.buyitapp.service.converters.Converter;

/**
 * @author Kristian Gombosh
 */
@Component
@Slf4j
public class CreateCategoryToCategoryConverter implements Converter<CreateCategoryRequest, Category> {
    @Override
    public Category convert(final CreateCategoryRequest createCategoryRequest) {
        log.info("Convert the create category request({}) to category", createCategoryRequest);
        return new Category(category -> {
            category.setName(createCategoryRequest.getName());
            category.setDescription(createCategoryRequest.getDescription());
        });
    }
}
