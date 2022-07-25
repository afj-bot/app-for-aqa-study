package com.afj.solution.productApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.afj.solution.productApp.exception.EntityAlreadyExistsException;
import com.afj.solution.productApp.model.User;
import com.afj.solution.productApp.repository.UserRepository;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(final User user) {
        if (userRepository
                .findByUsernameAndEmail(user.getUsername(), user.getEmail()).isEmpty()) {
            log.info("Save user with username({}) and email({})", user.getUsername(), user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new EntityAlreadyExistsException(User.class, String.format("%s %s",
                user.getEmail(), user.getUsername()));
    }
}
