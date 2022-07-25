package com.afj.solution.buyitapp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.Product;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllBy(Pageable pageable);
}
