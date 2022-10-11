package com.afj.solution.buyitapp.service.user;

import java.util.UUID;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.CreateUserRequest;
import com.afj.solution.buyitapp.payload.request.UpdateUserRequest;
import com.afj.solution.buyitapp.payload.response.UserResponse;

/**
 * @author Tomash Gombosh
 */
public interface UserService {

    User findById(UUID userId);

    boolean isUserExist(String username);

    User save(User user);

    User updateUser(UUID userId, UpdateUserRequest request);

    User updateUser(UUID userId, User user);

    User saveAnonymous();

    UserResponse getMe(UUID userId);

   void createUser(CreateUserRequest createUserRequest, UUID userId);
}
