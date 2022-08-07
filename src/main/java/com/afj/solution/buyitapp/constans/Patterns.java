package com.afj.solution.buyitapp.constans;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import com.afj.solution.buyitapp.common.Adapters;
import com.afj.solution.buyitapp.common.Error;
import com.afj.solution.buyitapp.common.Response;
import com.afj.solution.buyitapp.service.localize.TranslatorService;

import static java.util.Objects.isNull;

/**
 * @author Tomash Gombosh
 */
@Slf4j
public final class Patterns {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new Adapters.ZonedDateTimeAdapter())
            .create();

    public static final String RESPONSE_SUCCESS = "Success";
    public static final String STATUS_FAILED = "Failed";
    public static final String BAD_RESPONSE_LOG = "Returning failed result with status code 400.";
    public static final String NOT_FOUND_RESPONSE_LOG = "Returning failed result with status code 404.";

    private Patterns() {
        throw new AssertionError(" Utility classes should not have a public or default constructor. [HideUtilityClassConstructor]");
    }

    public static Response<String> generateErrorResponse(final Exception ex, final HttpStatus httpStatus) {
        final String identify = isNull(ex.getCause()) ? ex.getClass().getSimpleName() : ex.getCause().getMessage();
        final String message = isNull(ex.getMessage()) ? ex.getLocalizedMessage() : ex.getMessage();
        final Error error = new Error(message, identify);
        final Response.Status status = new Response.Status(httpStatus.toString(), error);
        log.error("Status -> {}, error -> {}", httpStatus.name(), error.getMessage());
        return new Response<>(STATUS_FAILED, status, httpStatus);
    }

    public static Response<String> generateErrorResponse(final NullPointerException ex) {
        final StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        final String identify = isNull(ex.getCause()) ? ex.getClass().getSimpleName() : ex.getCause().getMessage();
        final String message = String.format("%s", errors.toString());
        final Error error = new Error(message, identify);
        final Response.Status status = new Response.Status(HttpStatus.BAD_REQUEST.name(), error);
        log.error("Status -> {}, error -> {}", HttpStatus.BAD_REQUEST.name(), ex);
        return new Response<>(STATUS_FAILED, status, HttpStatus.BAD_REQUEST);
    }

    public static Response<String> generateErrorResponse(final IOException ex) {
        final StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        final String identify = isNull(ex.getCause()) ? ex.getClass().getSimpleName() : ex.getCause().getMessage();
        final String message = String.format("%s", errors.toString());
        final Error error = new Error(message, identify);
        final Response.Status status = new Response.Status(HttpStatus.BAD_REQUEST.name(), error);
        log.error("Status -> {}, error -> {}", HttpStatus.BAD_REQUEST.name(), ex);
        return new Response<>(STATUS_FAILED, status, HttpStatus.BAD_REQUEST);
    }

    public static String generateAccessDeniedErrorResponse() {
        final TranslatorService translator = new TranslatorService();
        log.error("Status -> 403, message -> Access denied, identify -> AccessDeniedException");

        final Error error = new Error(translator.toLocale("error.access.denied"), "AccessDeniedException");
        final Response.Status status = new Response.Status(HttpStatus.FORBIDDEN.name(), Collections.singletonList(error));
        final Response<String> response = new Response<>(STATUS_FAILED, status, HttpStatus.FORBIDDEN);
        return GSON.toJson(response);
    }

    public static String generateUnAuthorizedErrorResponse() {
        final TranslatorService translator = new TranslatorService();
        log.error("Status -> 401, message -> Un Authorized, identify -> AuthenticationException");

        return generateUnAuthorizedErrorResponse(translator.toLocale("error.access.unauthorized"));
    }

    public static String generateUnAuthorizedErrorResponse(final String message) {
        log.error("Status -> 401, message -> {}, identify -> AuthenticationException", message);

        final Error error = new Error(message, "AuthenticationException");
        final Response.Status status = new Response.Status(HttpStatus.UNAUTHORIZED.name(), Collections.singletonList(error));
        final Response<String> response = new Response<>(STATUS_FAILED, status, HttpStatus.UNAUTHORIZED);
        return GSON.toJson(response);
    }

    public static Response<String> generateSuccessResponse() {
        return new Response<>(RESPONSE_SUCCESS);
    }
}
