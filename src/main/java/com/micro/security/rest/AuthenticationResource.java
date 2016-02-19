package com.micro.security.rest;

import com.micro.security.model.CreateTokenRequest;
import com.micro.security.model.UserStoreService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Created by saipkri on 19/02/16.
 */
@RestController
public class AuthenticationResource {

    private static final Logger LOG = Logger.getLogger(AuthenticationResource.class);

    private final UserStoreService userStoreService;

    @Inject
    public AuthenticationResource(final UserStoreService userStoreService) {
        this.userStoreService = userStoreService;
    }

    @RequestMapping(value = "/token/new", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> createToken(@RequestBody final CreateTokenRequest createTokenRequest) {
        // Authenticate the user and return the details as a Token.
        if (LOG.isInfoEnabled()) {
            LOG.info(" ---> " + createTokenRequest);
        }
        return new ResponseEntity<String>(UUID.randomUUID().toString(), HttpStatus.OK);
    }
}
