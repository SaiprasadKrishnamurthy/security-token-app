package com.micro.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by saipkri on 19/02/16.
 */
@ApiModel(description = "Request to authenticate the user and create a new Json Web Token")
@Data
public class CreateTokenRequest {
    @ApiModelProperty(required = true, value = "user id")
    private String username;
    @ApiModelProperty(required = true, value = "password")
    private String password;
}
