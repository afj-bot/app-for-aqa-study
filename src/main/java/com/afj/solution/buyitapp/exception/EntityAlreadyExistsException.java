package com.afj.solution.buyitapp.exception;

import java.io.Serializable;

/**
 * @author Tomash Gombosh
 */
public class EntityAlreadyExistsException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 9093377008018707094L;

    public EntityAlreadyExistsException() {
        super();
    }

    public EntityAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }

    public EntityAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

    public EntityAlreadyExistsException(final Class<?> clazz) {
        this(String.format("%s entity already exists", clazz.getSimpleName()));
    }

    public EntityAlreadyExistsException(final Class<?> clazz, final String field) {
        this(String.format("%s entity with field %s already exists", clazz.getSimpleName(), field));
    }

    public EntityAlreadyExistsException(final Class<?> clazz, final Throwable cause) {
        this(String.format("%s entity already exists", clazz.getSimpleName()), cause);
    }

    public EntityAlreadyExistsException(final Class<?> clazz, final String field, final Throwable cause) {
        this(String.format("%s entity with field %s already exists", clazz.getSimpleName(), field), cause);
    }
}
