package com.afj.solution.buyitapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afj.solution.buyitapp.model.User;

/**
 * @author Tomash Gombosh
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndEmail(String username, String email);
}
