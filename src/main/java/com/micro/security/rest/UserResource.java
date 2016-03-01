package com.micro.security.rest;

import com.micro.security.model.User;
import com.micro.security.model.UserStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by saipkri on 19/02/16.
 */
@Api(value = "API Endpoint that returns all the user details from the user store (eg: LDAP)")
@RestController
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class);

    private final UserStoreService userStoreService;

    @Inject
    public UserResource(final UserStoreService userStoreService) {
        this.userStoreService = userStoreService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK - All good to go - a new signed JSon Web Token is returned as a text"),
                    @ApiResponse(code = 401, message = "Authentication failure")
            }
    )
    @ApiOperation(value = "Endpoint operation that gets all user details from the user store (eg: LDAP)")
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        LOG.info("Get All users");
        return new ResponseEntity<List<User>>(userStoreService.getAllUsers(), HttpStatus.OK);
    }
}
