package com.afj.solution.buyitapp.service.category;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.category.SubCategory;
import com.afj.solution.buyitapp.repository.SubcategoryRepository;
import com.afj.solution.buyitapp.service.localize.TranslatorService;

import static java.lang.String.format;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubcategoryRepository repository;

    private final TranslatorService translatorService;

    @Autowired
    public SubCategoryServiceImpl(final SubcategoryRepository repository,
                                  final TranslatorService translatorService) {
        this.repository = repository;
        this.translatorService = translatorService;
    }

    @Override
    public SubCategory findById(final UUID id) {
        log.info("Find subcategory by id({})", id);
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        format(translatorService.toLocale("error.subcategory.not-found"), id)));
    }

    @Override
    public void save(final SubCategory subCategory) {
        repository.save(subCategory);
    }
}
