package com.afj.solution.buyitapp.exception;

import java.io.Serializable;

import static com.afj.solution.buyitapp.constans.Patterns.FAILED_BAD_REQUEST;

/**
 * @author Tomash Gombosh
 */
public class BadRequestException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -3827423998643222098L;

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BadRequestException() {
        super(FAILED_BAD_REQUEST);
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final Throwable cause) {
        super(cause);
    }
}
