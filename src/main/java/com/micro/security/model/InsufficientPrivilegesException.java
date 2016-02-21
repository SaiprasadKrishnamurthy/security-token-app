package com.micro.security.model;

/**
 * Created by saipkri on 19/02/16.
 */
public class InsufficientPrivilegesException extends RuntimeException {

    private String message;

    public InsufficientPrivilegesException(final String message) {
        super(message);
    }
}
