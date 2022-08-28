package com.afj.solution.buyitapp.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tomash Gombosh
 */
public class EntityNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 692789665686851617L;

    public EntityNotFoundException(final String message) {
        super(message);
    }

}
