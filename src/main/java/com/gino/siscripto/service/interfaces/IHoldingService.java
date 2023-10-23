package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Holding;
import org.springframework.stereotype.Service;


public interface IHoldingService {
    Holding createHolding(Holding holding) throws ApiException;
}
