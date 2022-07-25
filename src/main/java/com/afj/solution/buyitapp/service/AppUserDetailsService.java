package com.afj.solution.buyitapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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
}
