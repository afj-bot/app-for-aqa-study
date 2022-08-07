package com.afj.solution.buyitapp.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tomash Gombosh
 */
public class EntityAlreadyExistsException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 9093377008018707094L;

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }

}
