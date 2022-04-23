package com.example.springangularadsapp.exception;

import com.example.springangularadsapp.models.ERole;

public class AccessValidationException extends RuntimeException {

    public AccessValidationException(ERole privileges) {
        super("You must possess at least " + privileges + " access privileges.");
    }
}
