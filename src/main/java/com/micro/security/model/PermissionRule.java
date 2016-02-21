package com.micro.security.model;

import lombok.Data;

import java.util.List;

/**
 * Created by saipkri on 20/02/16.
 */
@Data
public class PermissionRule {
    private List<String> requiredPermissions;
    private int minimumNumberOfPermissionsMatch;
}
