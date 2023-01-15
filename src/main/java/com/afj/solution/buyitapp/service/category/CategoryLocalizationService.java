package com.afj.solution.buyitapp.service.category;

import com.afj.solution.buyitapp.model.category.Category;

/**
 * @author Kristian Gombosh
 */
public interface CategoryLocalizationService {

    void save(Category category, String language);

}
