package com.micro.security.model;

import lombok.Data;

/**
 * Created by saipkri on 19/02/16.
 */
@Data
public class CreateTokenRequest {
    private String username;
    private String password;
    private String alternateCredentials;
}
