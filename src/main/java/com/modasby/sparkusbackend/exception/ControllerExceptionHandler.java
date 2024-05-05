package com.modasby.sparkusbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), request.getDescription(false), new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleDisabledException(DisabledException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), request.getDescription(false), new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), request.getDescription(false), new Date());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleUsernameNotFoundException() {
    }

    @ExceptionHandler(UserOrEmailAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> handleUserOrEmailAlreadyExists(UserOrEmailAlreadyExists e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), request.getDescription(false), new Date());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), request.getDescription(false), new Date());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
