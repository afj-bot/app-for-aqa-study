package com.afj.solution.buyitapp.service.converters;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.CreateUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Kristian Gombosh
 */
@Slf4j
@Component
public class CreateUserRequestToUser implements Converter<CreateUserRequest, User> {

    @Override
    public User convert(final CreateUserRequest createUserRequest) {
        log.info("Convert the Create User Request ({}) to user", createUserRequest);
        return new User(user -> {
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setUsername(createUserRequest.getUsername());
            user.setEmail(createUserRequest.getEmail());
            user.setDateOfBirth(createUserRequest.getDateOfBirth());
            user.setHomeAddress(createUserRequest.getHomeAddress());
            user.setPassword(createUserRequest.getPassword());
        });
    }
}
