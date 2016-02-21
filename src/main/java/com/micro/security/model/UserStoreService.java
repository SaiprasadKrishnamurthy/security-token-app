package com.micro.security.model;

import java.util.List;

/**
 * Created by saipkri on 19/02/16.
 */
public interface UserStoreService {
    User authenticateAndGetUserDetails(String username, String password);
    List<User> getAllUsers();
}
