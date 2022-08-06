package com.afj.solution.buyitapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.CustomAuthenticationException;
import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.model.UserLogin;
import com.afj.solution.buyitapp.repository.UserLoginRepository;
import com.afj.solution.buyitapp.repository.UserRepository;

/**
 * @author Kristian Gombosh
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    @Autowired
    UserLoginServiceImpl(final UserLoginRepository userLoginRepository,
                         final UserRepository userRepository) {
        this.userLoginRepository = userLoginRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(final User user) {
        userLoginRepository.save(new UserLogin(userLogin -> {
            userLogin.setUserId(user.getId());
            userLogin.setTotal(10);
        }));

    }

    @Override
    public void decreaseLoginAttempts(final User user) {
        final UserLogin userLogin = userLoginRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id"));
            userLogin.setTotal(userLogin.getTotal() - 1);
            userLoginRepository.save(userLogin);
    }

    @Override
    public void checkLoginAttempts(final User user) {
        final UserLogin userLogin = userLoginRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id"));
        if (userLogin.getTotal() == 0) {
            user.setEnabled(false);
            userRepository.save(user);
            throw new CustomAuthenticationException("User is blocked");
        }
        this.decreaseLoginAttempts(user);
        throw new CustomAuthenticationException(String.format("Invalid user credentials, you can try %s more times before the user is locked out",
                userLogin.getTotal()));
    }

    @Override
    public void updateLoginAttempts(User user) {
        final UserLogin userLogin = userLoginRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id"));
        if (userLogin.getTotal() != 10) {
            userLogin.setTotal(10);
            userLoginRepository.save(userLogin);
        }
    }
}
