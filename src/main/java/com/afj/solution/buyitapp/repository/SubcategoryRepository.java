package com.afj.solution.buyitapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.category.SubCategory;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface SubcategoryRepository extends JpaRepository<SubCategory, UUID> {

    Optional<SubCategory> findByCategoryId(UUID categoryId);
}
