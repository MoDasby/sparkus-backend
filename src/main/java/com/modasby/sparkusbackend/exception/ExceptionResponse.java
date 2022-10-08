package com.modasby.sparkusbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ExceptionResponse {

    private String description;
    private String message;
    private Date timestamp;
}
