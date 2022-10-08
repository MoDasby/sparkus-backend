package com.modasby.sparkusbackend.exception;

public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
