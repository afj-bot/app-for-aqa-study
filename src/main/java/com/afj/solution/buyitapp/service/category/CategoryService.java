package com.afj.solution.buyitapp.service.category;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.payload.request.CreateCategoryRequest;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;

/**
 * @author Tomash Gombosh
 */
public interface CategoryService {

    void save(CreateCategoryRequest createCategoryRequest, String language);

    Page<CategoryResponse> getCategories(Pageable pageable, String language);

    List<CategoryResponse> getLocalizeCategory(Pageable pageable, String language);

    Category getLocalizedCategory(Category category, String language);

    Category findById(UUID id);
}
