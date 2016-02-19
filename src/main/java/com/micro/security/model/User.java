package com.micro.security.model;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by saipkri on 19/02/16.
 */
@Data
public class User {
    private String id;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String roleTokens;
    private String permissionTokens;
    private Map<String, Object> attributes = new TreeMap<>();
}
