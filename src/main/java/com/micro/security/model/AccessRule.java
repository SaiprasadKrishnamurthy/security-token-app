package com.micro.security.model;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by saipkri on 20/02/16.
 */
@Data
public class AccessRule extends ResourceSupport {
    private String uid;
    private String uri;
    private String httpMethod;
    private PermissionRule permissionRule;
    private NumberOfTransactionsRule numberOfTransactionsRule;
}
