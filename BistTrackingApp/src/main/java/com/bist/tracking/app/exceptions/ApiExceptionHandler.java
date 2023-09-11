package com.bist.tracking.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
//                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                "Invalid credentials",
//                e,
                status,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, status);
    }
    @ExceptionHandler(value = {ApiForbiddenException.class})
    public ResponseEntity<Object> handleApiForbiddenException(ApiForbiddenException e) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                e.getMessage(),
//                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, forbidden);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleExceptıon(Exception e) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                e.getMessage(),
//                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, forbidden);
    }
}
