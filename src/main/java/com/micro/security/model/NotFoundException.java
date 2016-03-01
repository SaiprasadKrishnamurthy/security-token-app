package com.micro.security.model;

/**
 * Created by saipkri on 19/02/16.
 */
public class NotFoundException extends RuntimeException {

    private String message;

    public NotFoundException(final String message) {
        super(message);
    }
}
