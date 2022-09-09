package com.afj.solution.buyitapp.service.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.EntityAlreadyExistsException;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.repository.CategoryRepository;
import com.afj.solution.buyitapp.service.localize.TranslatorService;

import static java.lang.String.format;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final TranslatorService translatorService;

    public CategoryServiceImpl(final CategoryRepository repository,
                               final TranslatorService translatorService) {
        this.repository = repository;
        this.translatorService = translatorService;
    }
    @Override
    public void save(final Category category) {
        log.info("Save category {}", category);
        repository.findById(category.getId()).orElseThrow(
                () -> new EntityAlreadyExistsException(format(translatorService
                        .toLocale("error.category.exits"), category.getId())));
        repository.save(category);
    }
}
