package com.gino.siscripto.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class WalletDoesNotExist extends ApiException{
    public WalletDoesNotExist(UUID id){
        super("Wallet with "+id+" does not exist", HttpStatus.NOT_FOUND);
    }
}
