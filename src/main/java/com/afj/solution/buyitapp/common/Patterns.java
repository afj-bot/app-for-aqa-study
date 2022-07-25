package com.afj.solution.buyitapp.common;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public static final String FAILED_NOT_FOUND = String.format("%s. %s", STATUS_FAILED, NOT_FOUND_RESPONSE_LOG);
    public static final String FAILED_BAD_REQUEST = String.format("%s. %s", STATUS_FAILED, BAD_RESPONSE_LOG);

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


    public static Response<String> generateErrorResponse(final MethodArgumentNotValidException ex) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        final List<Error> errors = messages.stream()
                .map(message -> new Error(message, "MethodArgumentNotValidException"))
                .collect(Collectors.toList());

        final Response.Status status = new Response.Status(httpStatus.toString(), errors);
        log.error("Status -> {}, error -> {}", httpStatus.name(),
                errors.stream().map(Error::getMessage).collect(Collectors.toList()));
        return new Response<>(STATUS_FAILED, status, httpStatus);
    }

    public static String generateAccessDeniedErrorResponse() {
        log.error("Status -> 403, message -> Access denied, identify -> AccessDeniedException");

        final Error error = new Error("Access denied", "AccessDeniedException");
        final Response.Status status = new Response.Status(HttpStatus.FORBIDDEN.name(), Collections.singletonList(error));
        final Response<String> response = new Response<>(STATUS_FAILED, status, HttpStatus.FORBIDDEN);
        return GSON.toJson(response);
    }

    public static String generateUnAuthorizedErrorResponse() {
        log.error("Status -> 401, message -> Un Authorized, identify -> AuthenticationException");

        final Error error = new Error("Authentication required", "AuthenticationException");
        final Response.Status status = new Response.Status(HttpStatus.UNAUTHORIZED.name(), Collections.singletonList(error));
        final Response<String> response = new Response<>(STATUS_FAILED, status, HttpStatus.UNAUTHORIZED);
        return GSON.toJson(response);
    }

    public static Response<String> generateSuccessResponse() {
        return new Response<>(RESPONSE_SUCCESS);
    }
}
