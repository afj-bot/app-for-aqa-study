package com.afj.solution.qa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.qa.exception.CustomAuthenticationException;
import com.afj.solution.qa.model.User;
import com.afj.solution.qa.repository.UserRepository;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomAuthenticationException("Invalid username/password provided"));
    }
}
