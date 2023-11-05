package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExist extends ApiException {
    public UserAlreadyExist(String dni) {
        super("User " + dni + " already exists", HttpStatus.CONFLICT);
    }
}
