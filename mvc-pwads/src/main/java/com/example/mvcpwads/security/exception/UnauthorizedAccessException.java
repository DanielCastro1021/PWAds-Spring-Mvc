package com.example.mvcpwads.security.exception;

import com.example.mvcpwads.security.models.ERole;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(ERole privileges) {
        super("You must possess at least " + privileges + " access privileges.");
    }
}
