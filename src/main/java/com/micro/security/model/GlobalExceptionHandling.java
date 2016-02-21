package com.micro.security.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler({AuthenticationFailedException.class})
    @ResponseBody
    public ResponseEntity<?> authenticationFailed(final Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InsufficientPrivilegesException.class})
    @ResponseBody
    public ResponseEntity<?> authorizationFailed(final Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({TransactionsQuotaExceededException.class})
    @ResponseBody
    public ResponseEntity<?> quotaExceeded(final Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }
}