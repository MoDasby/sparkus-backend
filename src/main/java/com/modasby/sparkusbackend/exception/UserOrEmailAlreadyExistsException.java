package com.modasby.sparkusbackend.exception;

public class UserOrEmailAlreadyExistsException extends RuntimeException {

    public UserOrEmailAlreadyExistsException(String message) {
        super(message);
    }

}
