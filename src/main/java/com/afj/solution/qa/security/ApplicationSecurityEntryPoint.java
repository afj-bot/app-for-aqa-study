package com.afj.solution.qa.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.afj.solution.qa.common.Error;
import com.afj.solution.qa.common.Response;

/**
 * @author Tomash Gombosh
 */
@Slf4j
public class ApplicationSecurityEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {
    private static final Gson GSON = new GsonBuilder().create();

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

    private static String generateAccessDeniedErrorResponse() {
        log.error("Status -> 403, message -> Access denied, identify -> AccessDeniedException");

        final Error error = new Error("Access denied", "AccessDeniedException");
        final Response<String> response = new Response<>("Failed", HttpStatus.FORBIDDEN, error);
        return GSON.toJson(response);
    }

    private static String generateUnAuthorizedErrorResponse() {
        log.error("Status -> 401, message -> Un Authorized, identify -> AuthenticationException");

        final Error error = new Error("Authentication required", "AuthenticationException");
        final Response<String> response = new Response<>("Failed", HttpStatus.UNAUTHORIZED, error);
        return GSON.toJson(response);
    }
}
