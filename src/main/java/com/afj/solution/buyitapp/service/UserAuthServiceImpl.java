package com.afj.solution.buyitapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.CustomAuthenticationException;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.LoginRequest;
import com.afj.solution.buyitapp.repository.UserRepository;
import com.afj.solution.buyitapp.security.JwtTokenProvider;

/**
 * @author Tomash Gombosh
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserAuthServiceImpl(final UserRepository userRepository,
                               final JwtTokenProvider jwtTokenProvider,
                               final AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomAuthenticationException("Invalid username/password provided"));
    }

    @Override
    public String login(final LoginRequest loginRequest) {
        final String username = loginRequest.getUsername();
        final User user = this
                .findByUsername(loginRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                loginRequest.getPassword()));
        return jwtTokenProvider.createToken(user);
    }
}
