package com.afj.solution.buyitapp.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.afj.solution.buyitapp.common.Patterns.generateAccessDeniedErrorResponse;
import static com.afj.solution.buyitapp.common.Patterns.generateUnAuthorizedErrorResponse;

/**
 * @author Tomash Gombosh
 */
@Slf4j
public class ApplicationSecurityEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(final HttpServletRequest httpServletRequest,
                         final HttpServletResponse httpServletResponse,
                         final AuthenticationException exception) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(generateUnAuthorizedErrorResponse());

    }

    @Override
    public void handle(final HttpServletRequest httpServletRequest,
                       final HttpServletResponse httpServletResponse,
                       final AccessDeniedException exception)
            throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(generateAccessDeniedErrorResponse());
    }

}
