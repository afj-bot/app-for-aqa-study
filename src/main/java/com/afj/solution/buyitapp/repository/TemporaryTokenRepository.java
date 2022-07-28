package com.afj.solution.buyitapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.TemporaryToken;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface TemporaryTokenRepository extends JpaRepository<TemporaryToken, UUID> {
}
