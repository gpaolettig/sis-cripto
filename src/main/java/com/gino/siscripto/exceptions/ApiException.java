package com.gino.siscripto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
public class ApiException extends Exception{
    private final String message;
    private final HttpStatus httpStatuscode;
}
