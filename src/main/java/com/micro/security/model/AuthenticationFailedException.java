package com.micro.security.model;

/**
 * Created by saipkri on 19/02/16.
 */
public class AuthenticationFailedException extends RuntimeException {

    private String message;

    public AuthenticationFailedException(final String message) {
        super(message);
    }
}
