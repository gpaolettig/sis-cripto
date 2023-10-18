package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExists extends ApiException {
    public UserAlreadyExists(String dni) {
        super("User " + dni + " already exists", HttpStatus.CONFLICT);
    }
}
