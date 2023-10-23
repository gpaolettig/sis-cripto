package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class HoldingDoesNotExist extends ApiException{
    public HoldingDoesNotExist(){
        super("Holding does not exist", HttpStatus.NOT_FOUND);
    }
}
