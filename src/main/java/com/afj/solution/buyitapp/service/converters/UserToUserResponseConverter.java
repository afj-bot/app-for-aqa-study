package com.afj.solution.buyitapp.service.converters;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.response.UserResponse;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class UserToUserResponseConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(final User user) {
        log.info("Convert the user({}) to user response", user);
        return new UserResponse(userResponse -> {
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setDateOfBirth(user.getDateOfBirth());
            userResponse.setHomeAddress(user.getHomeAddress());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setCreatedAt(user.getCreatedAt());
            userResponse.setUpdatedAt(user.getUpdatedAt());
            userResponse.setUserRole(user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet()));
            userResponse.setAccountNonExpired(user.isAccountNonExpired());
            userResponse.setEnabled(user.isEnabled());
            userResponse.setAccountNonLocked(user.isAccountNonLocked());
            userResponse.setCredentialsNonExpired(user.isCredentialsNonExpired());
        });
    }
}
