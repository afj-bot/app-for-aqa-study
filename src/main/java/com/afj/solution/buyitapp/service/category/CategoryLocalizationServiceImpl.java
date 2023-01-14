package com.afj.solution.buyitapp.service.category;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.model.category.CategoryLocalization;
import com.afj.solution.buyitapp.repository.CategoryLocalizationRepository;
import com.afj.solution.buyitapp.service.converters.category.CategoryToCategoryLocalizationConverter;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class CategoryLocalizationServiceImpl implements CategoryLocalizationService {
    private final CategoryLocalizationRepository repository;

    private final CategoryToCategoryLocalizationConverter converter;


    public CategoryLocalizationServiceImpl(final CategoryLocalizationRepository repository,
                                           final CategoryToCategoryLocalizationConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void save(final Category category, final String language) {
        log.info("Save Localization category for {}", category.getId());
        final CategoryLocalization categoryLocalization = converter.convert(category);
        categoryLocalization.setLocale(Locale.UK.getCountry().toLowerCase(Locale.ROOT));
        repository.save(categoryLocalization);
    }

}
