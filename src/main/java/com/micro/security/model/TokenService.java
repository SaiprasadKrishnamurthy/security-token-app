package com.micro.security.model;

/**
 * Created by saipkri on 20/02/16.
 */
public interface TokenService {
    String getTokenForUser(User user);
}
