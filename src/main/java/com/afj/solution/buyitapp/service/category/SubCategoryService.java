package com.afj.solution.buyitapp.service.category;

import java.util.UUID;

import com.afj.solution.buyitapp.model.category.SubCategory;

/**
 * @author Tomash Gombosh
 */
public interface SubCategoryService {

    SubCategory findById(UUID id);
}
