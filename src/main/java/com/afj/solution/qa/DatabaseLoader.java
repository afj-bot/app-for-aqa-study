package com.afj.solution.qa;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.afj.solution.qa.model.User;
import com.afj.solution.qa.repository.UserRepository;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DatabaseLoader(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void run(final String... args) throws Exception {
        userRepository.save(new User(user -> {
            user.setId(UUID.randomUUID());
            user.setEmail("blackjneco@gmail.com");
            user.setUsername("blackjneco");
        }));
    }
}
