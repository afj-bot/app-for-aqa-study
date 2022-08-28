package com.afj.solution.buyitapp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.service.user.UserAuthService;

/**
 * @author Tomash Gombosh
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserAuthService userAuthService;

    @Autowired
    public AppUserDetailsService(final UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return userAuthService.findByUsername(username);
    }

    public UserDetails loadUserById(final UUID id) {
        return userAuthService.findById(id);
    }
}
