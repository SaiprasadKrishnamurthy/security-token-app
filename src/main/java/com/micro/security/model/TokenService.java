package com.micro.security.model;

/**
 * Created by saipkri on 20/02/16.
 */
public interface TokenService {
    String getTokenForUser(User user);
    void validateToken(final String token, final String uri, final String verb);
}
