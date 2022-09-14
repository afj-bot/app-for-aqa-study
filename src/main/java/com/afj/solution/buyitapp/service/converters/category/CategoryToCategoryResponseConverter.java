package com.afj.solution.buyitapp.service.converters.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.model.category.CategoryLocalization;
import com.afj.solution.buyitapp.model.category.SubCategory;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;
import com.afj.solution.buyitapp.payload.response.SubCategoryResponse;
import com.afj.solution.buyitapp.service.converters.Converter;

import static java.lang.String.format;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class CategoryToCategoryResponseConverter implements Converter<Category, CategoryResponse> {
    @Override
    public CategoryResponse convert(final Category category) {
        log.info("Convert the category({}) to response", category);
        final SubCategory subCategory = category.getSubCategories()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException(format(
                                "Something went wrong. "
                                        + "Enable to get subcategory of the category %s", category.getId())));
        final CategoryLocalization subCategoryLocalization = subCategory.getSubCategoryLocalizations()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException(format(
                                "Something went wrong. "
                                        + "Enable to get sub category localization of the category %s", category.getId())));
        final CategoryLocalization categoryLocalization = category
                .getLocalizations()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException(format(
                                "Something went wrong. "
                                        + "Enable to get category localization of the category %s", category.getId())));

        return new CategoryResponse(category.getId(),
                category.getName(),
                categoryLocalization.getName(),
                categoryLocalization.getDescription(),
                new SubCategoryResponse(subCategory.getId(), subCategoryLocalization.getName(), subCategoryLocalization.getDescription()));
    }
}
