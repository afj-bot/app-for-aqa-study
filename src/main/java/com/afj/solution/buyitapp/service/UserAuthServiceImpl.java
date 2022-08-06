package com.afj.solution.buyitapp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.CustomAuthenticationException;
import com.afj.solution.buyitapp.model.TemporaryToken;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.payload.request.LoginRequest;
import com.afj.solution.buyitapp.repository.UserRepository;
import com.afj.solution.buyitapp.security.JwtTokenProvider;

import static java.util.Objects.isNull;

/**
 * @author Tomash Gombosh
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final AnonymousCookieService anonymousCookieService;

    private final TemporaryTokenServiceImpl temporaryTokenService;

    private final UserLoginService userLoginService;

    @Autowired
    public UserAuthServiceImpl(final UserRepository userRepository,
                               final JwtTokenProvider jwtTokenProvider,
                               final AuthenticationManager authenticationManager,
                               final AnonymousCookieService anonymousCookieService,
                               final TemporaryTokenServiceImpl temporaryTokenService,
                               final UserLoginService userLoginService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.anonymousCookieService = anonymousCookieService;
        this.temporaryTokenService = temporaryTokenService;
        this.userLoginService = userLoginService;
    }

    @Override
    public User findByUsername(final String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new CustomAuthenticationException("error.username-password.invalid"));
    }

    @Override
    public User findById(final UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new CustomAuthenticationException("error.username-password.invalid"));
    }

    @Override
    public String login(final LoginRequest loginRequest) {
        final String username = loginRequest.getUsername();
        final User user = this
                .findByUsername(loginRequest.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    loginRequest.getPassword(), user.getAuthorities()));
            userLoginService.updateLoginAttempts(user);
        } catch (BadCredentialsException ex) {
            userLoginService.checkLoginAttempts(user);
        }
        return jwtTokenProvider.createToken(user);
    }

    @Override
    public String loginAnonymous(final String anonymousCookie, final UUID userId) {
        final Set<GrantedAuthority> roles = new HashSet<>(List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
        final AnonymousAuthenticationToken authenticationToken = new AnonymousAuthenticationToken("anonymous", userId.toString(), roles);
        authenticationManager.authenticate(authenticationToken);

        final Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("id", userId);
        claims.put("roles", roles);
        claims.put("username", "Anonymous");
        return jwtTokenProvider.createToken(claims);
    }

    @Override
    public Cookie checkAnonymousCookie(final Cookie[] cookies) {
        if (isNull(cookies) || cookies.length == 0) {
            throw new CustomAuthenticationException("error.cookie.invalid");
        }
        final Cookie cookie = Arrays.stream(cookies)
                .filter(c -> "anonymous".equals(c.getName()))
                .findFirst()
                .orElseThrow(() -> new CustomAuthenticationException("error.cookie.invalid"));
        final UUID decodeToken = anonymousCookieService.decodeAnonymousCookie(cookie);
        if (!temporaryTokenService.isTokenExist(decodeToken)) {
            throw new CustomAuthenticationException("error.cookie.invalid");
        }
        return cookie;
    }

    @Override
    public ResponseCookie generateAnonymousCookie() {
        final TemporaryToken temporaryToken = temporaryTokenService.save();
        return anonymousCookieService.generateAnonymousCookie(temporaryToken.getId());
    }
}
