package com.luantang.facebookapi.exceptions;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 2;
    public UserNotFoundException(String message) {
        super(message);
    }
}
