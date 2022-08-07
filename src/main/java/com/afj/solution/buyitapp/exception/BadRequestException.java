package com.afj.solution.buyitapp.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tomash Gombosh
 */
public class BadRequestException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -3827423998643222098L;

    public BadRequestException(final String message) {
        super(message);
    }
}
