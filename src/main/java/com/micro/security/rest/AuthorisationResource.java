package com.micro.security.rest;

import com.micro.security.model.TokenService;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * Created by saipkri on 21/02/16.
 */
@Api(value = "API Endpoint that performs all the necessary authorisation checks for the user identified by a token sent through the Authorization header using Bearer schema")
@RestController
public class AuthorisationResource {

    private static final Logger LOG = Logger.getLogger(AuthorisationResource.class);

    private final TokenService tokenService;

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK - All good to go"),
                    @ApiResponse(code = 401, message = "Unauthorized (you need to be authenticated)"),
                    @ApiResponse(code = 403, message = "You are authenticated but you don't have the right privileges to access this resource"),
                    @ApiResponse(code = 429, message = "You have exceeded your quota of accessing this resource"),
            }
    )
    @ApiOperation(value = "Endpoint operation that performs authorization checks for a Http request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization header must be of the format: Bearer: 'Your Jason Web Token')", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/authorise/*", method = RequestMethod.GET)
    public void performAuthorisationChecksGet(final HttpServletRequest request, @ApiParam(name = "_url", required = true, value = "The actual URL of the protected resource for which the authorization checks to be performed", example = "http://sai.com/product/1234") @RequestParam("_url") String url, @ApiParam(value = "The request method (http verb) invoked on the protected resource", example = "GET", required = true) @RequestParam("_requestMethod") String requestMethod, @RequestHeader(value = "Authorization") String bearerHeader, final HttpServletResponse response) throws Exception {
        authorise(request.getMethod(), url, bearerHeader);
    }

    @Inject
    public AuthorisationResource(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private void authorise(final String httpVerb, final String url, final String jsonWebToken) throws Exception {
        String uri = new URL(url).getPath();
        LOG.info("Authorise: "+httpVerb+", "+url+", "+jsonWebToken);
            if (!jsonWebToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header value must start with Bearer followed by your Json Web Token");
        }
        String token = jsonWebToken.replace("Bearer ", "");
        tokenService.validateToken(token, uri, httpVerb);
    }
}
