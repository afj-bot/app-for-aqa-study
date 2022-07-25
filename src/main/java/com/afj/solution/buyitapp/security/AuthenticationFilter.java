package com.afj.solution.buyitapp.security;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.afj.solution.buyitapp.service.AppUserDetailsService;

/**
 * @author Tomash Gombosh
 */
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtProvider;

    @Autowired
    private AppUserDetailsService service;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String jwt = jwtProvider.getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            final Collection<? extends GrantedAuthority> roles = jwtProvider.getRoleFromToken(jwt)
                    .stream()
                    .map(r -> new SimpleGrantedAuthority(r.get("authority")))
                    .toList();
            final String username = jwtProvider.getUsernameFromToken(jwt);
            final UserDetails userDetails = service.loadUserByUsername(username);
            if (userDetails.isAccountNonLocked() && userDetails.isEnabled()) {
                final UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        roles);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
