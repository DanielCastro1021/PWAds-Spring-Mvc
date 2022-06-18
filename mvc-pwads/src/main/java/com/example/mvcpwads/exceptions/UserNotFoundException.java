package com.example.mvcpwads.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Could not find user with this username : " + username + " .");
    }
}
