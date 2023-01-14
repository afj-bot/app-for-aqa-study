package com.afj.solution.buyitapp.service.category;

import com.afj.solution.buyitapp.model.category.Category;

/**
 * @author Tomash Gombosh
 */
public interface CategoryLocalizationService {

    void save(Category category, String language);

}
