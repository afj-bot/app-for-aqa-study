package com.afj.solution.buyitapp.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.CustomAuthenticationException;
import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.model.UserLogin;
import com.afj.solution.buyitapp.repository.UserLoginRepository;
import com.afj.solution.buyitapp.repository.UserRepository;
import com.afj.solution.buyitapp.service.localize.TranslatorService;

/**
 * @author Kristian Gombosh
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final TranslatorService translator;

    @Autowired
    UserLoginServiceImpl(final UserLoginRepository userLoginRepository,
                         final UserRepository userRepository,
                         final TranslatorService translator) {
        this.userLoginRepository = userLoginRepository;
        this.userRepository = userRepository;
        this.translator = translator;
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
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(translator
                                .toLocale("error.user.not-found"), user.getId())));
        userLogin.setTotal(userLogin.getTotal() - 1);
        userLoginRepository.save(userLogin);
    }

    @Override
    public void checkLoginAttempts(final User user) {
        if (userLoginRepository.findByUserId(user.getId()).isEmpty()) {
            userLoginRepository.save(new UserLogin(userLogin -> {
                userLogin.setUserId(user.getId());
                userLogin.setTotal(10);
            }));
            return;
        }
        final UserLogin userLogin = userLoginRepository.findByUserId(user.getId()).get();
        if (userLogin.getTotal() == 0) {
            user.setEnabled(false);
            userRepository.save(user);
            throw new CustomAuthenticationException(translator.toLocale("error.user.locked-out"));
        }
        this.decreaseLoginAttempts(user);
        throw new CustomAuthenticationException(String.format(translator
                        .toLocale("error.credential.locked-out"),
                userLogin.getTotal()));
    }

    @Override
    public void updateLoginAttempts(final User user) {
        final UserLogin userLogin = userLoginRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(translator
                                .toLocale("error.user.not-found"), user.getId())));
        final int successLoginNumber = 10;
        if (userLogin.getTotal() != successLoginNumber) {
            userLogin.setTotal(10);
            userLoginRepository.save(userLogin);
        }
    }
}
