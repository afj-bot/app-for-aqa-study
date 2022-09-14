package com.afj.solution.buyitapp.service.category;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.EntityAlreadyExistsException;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;
import com.afj.solution.buyitapp.repository.CategoryRepository;
import com.afj.solution.buyitapp.service.converters.category.CategoryToCategoryResponseConverter;
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

    private final CategoryToCategoryResponseConverter converter;

    public CategoryServiceImpl(final CategoryRepository repository,
                               final TranslatorService translatorService,
                               final CategoryToCategoryResponseConverter converter) {
        this.repository = repository;
        this.translatorService = translatorService;
        this.converter = converter;
    }

    @Override
    public void save(final Category category) {
        log.info("Save category {}", category);
        repository.findById(category.getId()).orElseThrow(
                () -> new EntityAlreadyExistsException(format(translatorService
                        .toLocale("error.category.exits"), category.getId())));
        repository.save(category);
    }

    @Override
    public Page<CategoryResponse> getCategories(final Pageable pageable, final String language) {
        return new PageImpl<>(repository.findAll(pageable)
                .stream()
                .peek(c -> c.setLocalizations(c
                        .getLocalizations()
                        .stream()
                        .filter(l -> l.getLocale().getLanguage().equals(language))
                        .collect(Collectors.toSet())))
                .peek(c -> c.setSubCategories(
                        c.getSubCategories()
                                .stream()
                                .peek(s -> s.setSubCategoryLocalizations(s
                                        .getSubCategoryLocalizations()
                                        .stream()
                                        .filter(l -> l.getLocale().getLanguage().equals(language))
                                        .collect(Collectors.toSet())))
                                .collect(Collectors.toSet())
                ))
                .map(converter::convert)
                .collect(Collectors.toList()));
    }

}
