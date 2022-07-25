package com.afj.solution.buyitapp.service;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.LoginRequest;

/**
 * @author Tomash Gombosh
 */
public interface UserAuthService {

    User findByUsername(String username);

    String login(LoginRequest loginRequest);
}
