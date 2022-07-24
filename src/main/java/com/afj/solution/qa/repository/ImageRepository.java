package com.afj.solution.qa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.qa.model.Image;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
}
