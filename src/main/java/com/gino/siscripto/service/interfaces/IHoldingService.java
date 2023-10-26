package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.key.HoldingKey;


public interface IHoldingService {
    Holding createHolding(Holding holding) throws ApiException;
    //update holding
    Holding deleteHolding(HoldingKey key) throws ApiException;
    Holding updateHolding(Holding holdingRequest, HoldingKey key, int flag) throws ApiException;
    Boolean checkHoldingAmount(Holding holding) throws ApiException;
    Boolean checkHolding(HoldingKey holdingKey);
}
