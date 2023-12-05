package com.luantang.facebookapi.exceptions;

public class JwtInvalidException extends RuntimeException {
    private static final long serialVersionUID = 3;
    public JwtInvalidException(String message) {
        super(message);
    }
}
