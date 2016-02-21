package com.micro.security.model;


import java.sql.Date;

/**
 * Created by saipkri on 21/02/16.
 */
public class TransactionLog {
    private String userId;
    private String uri;
    private String verb;
    private int numberOfTransactions;
    private int expiresAfterSeconds;
}
