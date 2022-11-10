package com.afj.solution.buyitapp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.model.product.Product;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllBy(Pageable pageable);

    @Query("SELECT c FROM Product c WHERE (c.user = :user) and (:name is null or c.name = :name) and (:description is null"
            + " or c.description = :description)")
    Page<Product> findAllByUserAndNameAndDescription(Pageable pageable, @Param("user") User user,
                                                     @Param("name") String name, @Param("description") String description);
}
