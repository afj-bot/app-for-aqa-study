package com.afj.solution.buyitapp.service;

import java.util.UUID;

import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.CreateUserRequest;
import com.afj.solution.buyitapp.payload.response.UserResponse;

/**
 * @author Tomash Gombosh
 */
public interface UserService {

    User save(User user);

    User saveAnonymous();

    UserResponse getMe(UUID userId);

   void createUser(CreateUserRequest createUserRequest, UUID userId);
}
