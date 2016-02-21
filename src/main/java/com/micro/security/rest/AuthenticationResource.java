package com.micro.security.rest;

import com.micro.security.model.CreateTokenRequest;
import com.micro.security.model.TokenService;
import com.micro.security.model.User;
import com.micro.security.model.UserStoreService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by saipkri on 19/02/16.
 */
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

    @RequestMapping(value = "/token/new", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> createToken(@RequestBody final CreateTokenRequest createTokenRequest) {
        // Authenticate the user and return the details as a Token.
        User user = userStoreService.authenticateAndGetUserDetails(createTokenRequest.getUsername(), createTokenRequest.getPassword());
        String token = tokenService.getTokenForUser(user);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
