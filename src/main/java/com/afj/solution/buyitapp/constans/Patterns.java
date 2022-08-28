package com.afj.solution.buyitapp.constans;

import java.time.ZonedDateTime;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import com.afj.solution.buyitapp.common.Adapters;
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
    private static final TranslatorService TRANSLATOR_SERVICE = new TranslatorService();

    private Patterns() {
        throw new AssertionError(" Utility classes should not have a public or default constructor. [HideUtilityClassConstructor]");
    }

    public static Response<String> generateErrorResponse(final Exception ex) {
        final String message = isNull(ex.getMessage()) ? ex.getLocalizedMessage() : ex.getMessage();
        final Response.Error error = new Response.Error(message);
        log.error("Exception -> {}, error -> {}", ex.getMessage(), error.getMessage());
        return new Response<>(STATUS_FAILED, Collections.singletonList(error));
    }

    public static String generateAccessDeniedErrorResponse() {
        log.error("Status -> 403, message -> Access denied, identify -> AccessDeniedException");

        final Response.Error error = new Response.Error(TRANSLATOR_SERVICE.toLocale("error.access.denied"));
        final Response<String> response = new Response<>(STATUS_FAILED, Collections.singletonList(error));
        return GSON.toJson(response);
    }

    public static String generateUnAuthorizedErrorResponse() {
        final TranslatorService translator = new TranslatorService();
        log.error("Status -> 401, message -> Un Authorized, identify -> AuthenticationException");

        return generateUnAuthorizedErrorResponse(translator.toLocale("error.access.unauthorized"));
    }

    public static String generateUnAuthorizedErrorResponse(final String message) {
        log.error("Status -> 401, message -> {}, identify -> AuthenticationException", message);

        final Response.Error error = new Response.Error(message);
        final Response<String> response = new Response<>(STATUS_FAILED, Collections.singletonList(error));
        return GSON.toJson(response);
    }

    public static Response<String> generateSuccessResponse() {
        return new Response<>(RESPONSE_SUCCESS);
    }
}
