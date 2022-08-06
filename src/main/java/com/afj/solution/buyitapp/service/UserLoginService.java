package com.afj.solution.buyitapp.service;

import com.afj.solution.buyitapp.model.User;

/**
 * @author Kristian Gombosh
 */
public interface UserLoginService {

    void save(User user);

    void decreaseLoginAttempts(User user);

    void checkLoginAttempts(User user);

    void updateLoginAttempts(User user);
}
