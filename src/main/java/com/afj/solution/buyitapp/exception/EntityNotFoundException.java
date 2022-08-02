package com.afj.solution.buyitapp.exception;

import java.io.Serializable;

import static com.afj.solution.buyitapp.constans.Patterns.FAILED_NOT_FOUND;

/**
 * @author Tomash Gombosh
 */
public class EntityNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 692789665686851617L;

    public EntityNotFoundException() {
        super(FAILED_NOT_FOUND);
    }

    public EntityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(final String message) {
        super(message);
    }

    public EntityNotFoundException(final Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(final Class<?> clazz) {
        this(String.format("error.%s.not-found", clazz.getSimpleName().toLowerCase()));
    }

    public EntityNotFoundException(final Class<?> clazz, final String field) {
        this(String.format("error.%s.not-found.%s", clazz.getSimpleName().toLowerCase(), field));
    }

    public EntityNotFoundException(final Class<?> clazz, final Throwable cause) {
        this(String.format("error.%s.not-found", clazz.getSimpleName().toLowerCase()), cause);
    }

    public EntityNotFoundException(final Class<?> clazz, final String field, final Throwable cause) {
        this(String.format("error.%s.not-found.%s", clazz.getSimpleName().toLowerCase(), field), cause);
    }
}
