package com.micro.security.model;

import lombok.Data;

/**
 * Created by saipkri on 20/02/16.
 */
@Data
public class AccessRule {
    private String id;
    private String uri;
    private String httpMethod;
    private PermissionRule permissionRule;
    private NumberOfTransactionsRule numberOfTransactionsRule;
}
