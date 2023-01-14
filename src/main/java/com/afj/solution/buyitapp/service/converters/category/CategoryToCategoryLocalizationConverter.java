package com.afj.solution.buyitapp.service.converters.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.model.category.CategoryLocalization;
import com.afj.solution.buyitapp.service.converters.Converter;

/**
 * @author Kristian Gombosh
 */
@Component
@Slf4j
public class CategoryToCategoryLocalizationConverter implements Converter<Category, CategoryLocalization> {
    @Override
    public CategoryLocalization convert(final Category category) {
        log.info("Convert the category to to Localization category");
        return new CategoryLocalization(localization -> {
            localization.setCategory(category);
            localization.setSubCategory(category.getSubCategories().iterator().next());
            localization.setName(category.getName());
            localization.setDescription(category.getDescription());
        });
    }

}
