package com.afj.solution.qa.service;

import org.springframework.stereotype.Service;

import com.afj.solution.qa.model.User;

public interface UserAuthService {

    User findByUsername(String username);
}
