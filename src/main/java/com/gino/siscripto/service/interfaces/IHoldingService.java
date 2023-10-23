package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.HoldingDoesNotExist;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.key.HoldingKey;
import org.springframework.stereotype.Service;


public interface IHoldingService {
    Holding createHolding(Holding holding) throws ApiException;
    //update holding
    Holding deleteHolding(HoldingKey key) throws ApiException;
    Holding updateHolding(Holding holdingrequest, HoldingKey key) throws ApiException;
}
