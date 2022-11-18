package com.afj.solution.buyitapp.service.category;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.EntityAlreadyExistsException;
import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.model.category.SubCategory;
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
        return new PageImpl<>(this.getLocalizeCategory(pageable, language));
    }

    @Override
    public List<CategoryResponse> getLocalizeCategory(final Pageable pageable, final String language) {
        return repository.findAll(pageable)
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
                .toList();
    }

    @Override
    public Category getLocalizedCategory(final Category category, final String language) {
        final Set<SubCategory> subCategoryLocalization =
                category
                        .getSubCategories()
                        .parallelStream()
                        .peek(s -> s.setSubCategoryLocalizations(s.getSubCategoryLocalizations()
                                .stream()
                                .filter(l -> l.getLocale().getLanguage().equals(language))
                                .collect(Collectors.toSet())))
                        .collect(Collectors.toSet());
        category.setLocalizations(category
                .getLocalizations()
                .parallelStream()
                .filter(l -> l.getLocale().getLanguage().equals(language))
                .collect(Collectors.toSet()));
        category.setSubCategories(subCategoryLocalization);
        return category;
    }

    @Override
    public Category findById(final UUID id) {
        log.info("Find category by id({})", id);
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        format(translatorService.toLocale("error.category.not-found"), id)));
    }

}
