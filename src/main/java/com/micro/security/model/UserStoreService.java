package com.micro.security.model;

/**
 * Created by saipkri on 19/02/16.
 */
public interface UserStoreService {
    User authenticateAndGetUserDetails(String username, String password);
}
