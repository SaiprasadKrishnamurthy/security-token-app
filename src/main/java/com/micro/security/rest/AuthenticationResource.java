package com.micro.security.rest;

import com.micro.security.model.CreateTokenRequest;
import com.micro.security.model.TokenService;
import com.micro.security.model.User;
import com.micro.security.model.UserStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by saipkri on 19/02/16.
 */
@Api(value = "API Endpoint that authenticates a user against a user data store (eg: LDAP) and issues a JSON Web Token upon successful authentication.")
@RestController
public class AuthenticationResource  {

    private static final Logger LOG = Logger.getLogger(AuthenticationResource.class);

    private final UserStoreService userStoreService;
    private final TokenService tokenService;

    @Inject
    public AuthenticationResource(final UserStoreService userStoreService, final TokenService tokenService) {
        this.userStoreService = userStoreService;
        this.tokenService = tokenService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK - All good to go - a new signed JSon Web Token is returned as a text"),
                    @ApiResponse(code = 401, message = "Authentication failure")
            }
    )
    @ApiOperation(value = "Endpoint operation that authenticates a user and returns a new signed Json Web Token on success.")
    @RequestMapping(value = "/token/new", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> createToken(@RequestBody final CreateTokenRequest createTokenRequest) {
        // Authenticate the user and return the details as a Token.
        User user = userStoreService.authenticateAndGetUserDetails(createTokenRequest.getUsername(), createTokenRequest.getPassword());
        String token = tokenService.getTokenForUser(user);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
