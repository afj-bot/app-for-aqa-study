package com.afj.solution.buyitapp.service.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;

/**
 * @author Tomash Gombosh
 */
public interface CategoryService {

    void save(Category category);

    Page<CategoryResponse> getCategories(Pageable pageable, String language);
}
