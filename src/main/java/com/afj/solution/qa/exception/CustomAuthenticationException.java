package com.afj.solution.qa.exception;


import java.io.Serializable;

/**
 * @author Tomash Gombosh
 */
public class CustomAuthenticationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -7944860703662008604L;

    public CustomAuthenticationException(final String msg, final Throwable throwable) {
        super(msg, throwable);
    }

    public CustomAuthenticationException(final String msg) {
        super(msg);
    }
}