package com.micro.security.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

/**
 * Created by saipkri on 21/02/16.
 */
@RestController
public class AuthorisationResource {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization header must be of the format: Bearer: 'Your Jason Web Token')", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/authorise/*", method = RequestMethod.GET)
    public void performAuthorisationChecksGet(final HttpServletRequest request, @RequestParam("_url") String url, @RequestHeader(value = "Authorization") String bearerHeader) {
        authorise(request.getMethod(), url, bearerHeader);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization header must be of the format: Bearer: 'Your Jason Web Token')", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/authorise/*", method = RequestMethod.POST)
    public void performAuthorisationChecksPost(final HttpServletRequest request, @RequestParam("_url") String url, @RequestHeader(value = "Authorization") String bearerHeader) {
        authorise(request.getMethod(), url, bearerHeader);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization header must be of the format: Bearer: 'Your Jason Web Token')", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/authorise/*", method = RequestMethod.PUT)
    public void performAuthorisationChecksPut(final HttpServletRequest request, @RequestParam("_url") String url, @RequestHeader(value = "Authorization") String bearerHeader) {
        authorise(request.getMethod(), url, bearerHeader);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization header must be of the format: Bearer: 'Your Jason Web Token')", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/authorise/*", method = RequestMethod.DELETE)
    public void performAuthorisationChecksDelete(final HttpServletRequest request, @RequestParam("_url") String url, @RequestHeader(value = "Authorization") String bearerHeader) {
        authorise(request.getMethod(), url, bearerHeader);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization header must be of the format: Bearer: 'Your Jason Web Token')", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/authorise/*", method = RequestMethod.HEAD)
    public void performAuthorisationChecksHead(final HttpServletRequest request, @RequestParam("_url") String url, @RequestHeader(value = "Authorization") String bearerHeader) {
        authorise(request.getMethod(), url, bearerHeader);
    }

    private void authorise(final String httpVerb, final String url, final String jsonWebToken) {
        try {
            String uri = new URL(url).getPath();
            if (!jsonWebToken.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Authorization header value must start with Bearer followed by your Json Web Token");
            }
            String token = jsonWebToken.replace("Bearer ", "");
            System.out.println(uri + "\n" + token);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
