package com.afj.solution.buyitapp.service;

import java.util.UUID;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.response.UserResponse;

/**
 * @author Tomash Gombosh
 */
public interface UserService {

    User save(User user);

    UserResponse getMe(UUID userId);
}
