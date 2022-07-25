package com.afj.solution.productApp.service;

import com.afj.solution.productApp.model.User;
import com.afj.solution.productApp.payload.request.LoginRequest;

public interface UserAuthService {

    User findByUsername(String username);

    String login(LoginRequest loginRequest);
}
