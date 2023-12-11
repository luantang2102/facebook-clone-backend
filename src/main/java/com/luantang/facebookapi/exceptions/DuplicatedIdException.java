package com.luantang.facebookapi.exceptions;

public class DuplicatedIdException extends RuntimeException {
    private static final long serialVersionUID = 4;
    public DuplicatedIdException(String message) {
        super(message);
    }
}
