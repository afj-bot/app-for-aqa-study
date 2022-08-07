package com.afj.solution.buyitapp.service.user;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.EntityAlreadyExistsException;
import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.CreateUserRequest;
import com.afj.solution.buyitapp.payload.response.UserResponse;
import com.afj.solution.buyitapp.repository.UserRepository;
import com.afj.solution.buyitapp.service.converters.user.CreateUserRequestToUser;
import com.afj.solution.buyitapp.service.converters.user.UserToResponseConverter;
import com.afj.solution.buyitapp.service.localize.TranslatorService;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToResponseConverter converter;
    private final CreateUserRequestToUser createUserRequestToUser;
    private final UserLoginServiceImpl userLoginService;
    private final TranslatorService translator;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder,
                           final UserToResponseConverter converter,
                           final CreateUserRequestToUser createUserRequestToUser,
                           final UserLoginServiceImpl userLoginService,
                           final TranslatorService translator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
        this.createUserRequestToUser = createUserRequestToUser;
        this.userLoginService = userLoginService;
        this.translator = translator;
    }

    @Override
    public User findById(final UUID userId) {
        log.info("Find user by id {}", userId);
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        translator.toLocale("error.user.not-found"), userId
                )));
    }

    @Override
    public boolean isUserExist(final String username) {
        return userRepository
                .findByUsername(username)
                .isPresent();
    }

    @Override
    public User save(final User user) {
        if (userRepository
                .findByUsernameAndEmail(user.getUsername(), user.getEmail()).isEmpty()) {
            log.info("Save user with username({}) and email({})", user.getUsername(), user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new EntityAlreadyExistsException(String.format(
                translator.toLocale("error.user.exits"), user.getUsername()
        ));
    }

    @Override
    public User saveAnonymous() {
        return userRepository.save(new User(user -> {
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            user.setCredentialsNonExpired(true);
            user.setAuthorities(new HashSet<>(List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
        }));
    }

    @Override
    public UserResponse getMe(final UUID userId) {
        final User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        translator.toLocale("error.user.not-found"), userId
                )));
        log.info("Find user by id {}", user);

        return converter.convert(user);
    }

    @Override
    public void createUser(final CreateUserRequest createUserRequest, final UUID userId) {
        final User existingUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        translator.toLocale("error.user.not-found"), userId
                )));
        final User newUser = createUserRequestToUser.convert(createUserRequest);
        existingUser.update(newUser, existingUser);
        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(existingUser);
        userLoginService.save(existingUser);
    }
}
