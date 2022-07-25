package com.afj.solution.buyitapp.exception;


import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Tomash Gombosh
 */
public class CustomAuthenticationException extends AuthenticationException implements Serializable {

    private static final long serialVersionUID = -7944860703662008604L;

    public CustomAuthenticationException(final String msg, final Throwable throwable) {
        super(msg, throwable);
    }

    public CustomAuthenticationException(final String msg) {
        super(msg);
    }
}
