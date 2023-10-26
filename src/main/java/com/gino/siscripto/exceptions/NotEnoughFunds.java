package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NotEnoughFunds extends ApiException{
    public NotEnoughFunds(UUID walletId){
        super("The wallet with ID "+walletId +" does not have sufficient funds", HttpStatus.PAYMENT_REQUIRED);

    }
}
