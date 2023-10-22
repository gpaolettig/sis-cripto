package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

public class CurrencyDoesNotExist extends ApiException{
    public CurrencyDoesNotExist(String ticker){
        super("Currency "+ticker+" does not exist", HttpStatus.NOT_FOUND);
    }
}
