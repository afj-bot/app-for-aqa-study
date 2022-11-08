package com.afj.solution.buyitapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.product.Rating;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
}
