package com.micro.security.model;

import lombok.Data;

/**
 * Created by saipkri on 20/02/16.
 */
@Data
public class NumberOfTransactionsRule {
    private int maxNumberOfTransactionsAllowed;
    private long timeAllowedInSeconds;
}
