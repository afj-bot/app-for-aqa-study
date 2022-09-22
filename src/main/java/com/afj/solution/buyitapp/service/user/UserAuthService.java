package com.afj.solution.buyitapp.service.user;

import java.util.UUID;
import javax.servlet.http.Cookie;

import org.springframework.http.ResponseCookie;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.LoginRequest;

/**
 * @author Tomash Gombosh
 */
public interface UserAuthService {

    User findByUsername(String username);

    User findById(UUID id);

    Object login(LoginRequest loginRequest);

    String loginAnonymous(String anonymousCookie, UUID userId);

    Cookie checkAnonymousCookie(Cookie... cookies);

    ResponseCookie generateAnonymousCookie();
}
