package com.afj.solution.buyitapp.exception;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.afj.solution.buyitapp.common.Response;

import static com.afj.solution.buyitapp.common.Patterns.generateErrorResponse;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomAuthenticationException.class)
    public Response<String> customAuthenticationExceptionHandler(final CustomAuthenticationException ex) {
        return generateErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public Response<String> entityAlreadyExistsExceptionHandler(final EntityAlreadyExistsException ex) {
        return generateErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Response<String> entityNotFoundExceptionHandler(final EntityNotFoundException ex) {
        return generateErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Response<String> badRequestExceptionHandler(final BadRequestException ex) {
        return generateErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NullPointerException.class, IOException.class})
    public Response<String> generalExceptionHandling(final Exception ex) {
        return generateErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<String> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        return generateErrorResponse(ex);
    }

}
