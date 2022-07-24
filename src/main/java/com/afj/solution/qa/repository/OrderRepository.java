package com.afj.solution.qa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderRepository, UUID> {
}
