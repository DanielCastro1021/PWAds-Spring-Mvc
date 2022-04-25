package com.example.springangularadsapp.security.exception;

import com.example.springangularadsapp.enumerations.ERole;

public class AccessValidationException extends RuntimeException {

    public AccessValidationException(ERole privileges) {
        super("You must possess at least " + privileges + " access privileges.");
    }
}
