package com.luantang.facebookapi.exceptions;

public class PostNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;
    public PostNotFoundException(String message) {
        super(message);
    }
}
