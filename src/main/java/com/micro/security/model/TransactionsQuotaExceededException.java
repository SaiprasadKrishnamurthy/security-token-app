package com.micro.security.model;

/**
 * Created by saipkri on 19/02/16.
 */
public class TransactionsQuotaExceededException extends RuntimeException {

    private String message;

    public TransactionsQuotaExceededException(final String message) {
        super(message);
    }
}
