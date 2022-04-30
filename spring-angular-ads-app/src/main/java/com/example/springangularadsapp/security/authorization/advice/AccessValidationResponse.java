package com.example.springangularadsapp.security.authorization.advice;

import com.example.springangularadsapp.security.exception.AccessValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccessValidationResponse {
    @ResponseBody
    @ExceptionHandler(AccessValidationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String accessPrivilegesNotFoundHandler(AccessValidationException ex) {
        return ex.getMessage();
    }
}