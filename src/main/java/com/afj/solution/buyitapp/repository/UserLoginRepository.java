package com.afj.solution.buyitapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.UserLogin;

/**
 * @author Kristian Gombosh
 */
@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, UUID> {

    Optional<UserLogin> findByUserId(UUID userId);
}
