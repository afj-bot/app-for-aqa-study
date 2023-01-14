package com.afj.solution.buyitapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.category.CategoryLocalization;

/**
 * @author Kristian Gombosh
 */
@Repository
public interface CategoryLocalizationRepository extends JpaRepository<CategoryLocalization, UUID> {
}
