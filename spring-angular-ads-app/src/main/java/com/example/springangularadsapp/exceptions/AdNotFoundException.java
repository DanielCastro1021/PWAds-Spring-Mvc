package com.example.springangularadsapp.exceptions;

public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException(String id) {
        super("Could not find ad with this id : " + id + " .");
    }
}
