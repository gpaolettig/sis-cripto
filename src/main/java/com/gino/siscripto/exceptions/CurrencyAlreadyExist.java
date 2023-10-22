package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class CurrencyAlreadyExist extends ApiException{
    public CurrencyAlreadyExist(String ticker){
        super("Currency "+ticker+" already exist", HttpStatus.CONFLICT);
    }
}
