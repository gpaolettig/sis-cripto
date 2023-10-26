package com.gino.siscripto.exceptions;

import com.gino.siscripto.model.entity.Holding;
import org.springframework.http.HttpStatus;

public class HoldingDoesNotExist extends ApiException{
    public HoldingDoesNotExist(Holding holding){
        super("Holding  "+ holding.getId().getId_wallet() + " "+
                holding.getId().getTicker_currency() +" "+ holding.getAmount()
                +" does not exist", HttpStatus.NOT_FOUND);
    }
}
