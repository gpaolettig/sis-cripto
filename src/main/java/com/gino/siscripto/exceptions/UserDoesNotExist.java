package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class UserDoesNotExist extends ApiException {

    public UserDoesNotExist(String dni){
        super("User " + dni + " does not exists", HttpStatus.NOT_FOUND);

    }
}
