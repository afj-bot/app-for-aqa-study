package com.afj.solution.buyitapp;

import java.util.Set;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.exception.EntityAlreadyExistsException;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.service.category.CategoryService;
import com.afj.solution.buyitapp.service.category.CategoryServiceImpl;
import com.afj.solution.buyitapp.service.user.UserService;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class DatabaseLoader implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public DatabaseLoader(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(final String... args) {
        if (!userService.isUserExist("blackjneco")) {
            userService.save(new User(user -> {
                user.setId(UUID.randomUUID());
                user.setEmail("blackjneco@gmail.com");
                user.setUsername("blackjneco");
                user.setPassword("Test123$");
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setEnabled(true);
                user.setCredentialsNonExpired(true);
                user.setAuthorities(Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")));
            }));
        }
    }
}
