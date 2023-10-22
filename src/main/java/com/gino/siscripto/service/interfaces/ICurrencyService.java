package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.CurrencyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.CurrencyAlreadyExist;
import com.gino.siscripto.model.entity.Currency;

public interface ICurrencyService {
    CurrencyDTO createCurrency(CurrencyDTO currencyDTO) throws ApiException;
    CurrencyDTO updateCurrency(String ticker,CurrencyDTO currencyDTO) throws ApiException;
    CurrencyDTO deleteCurrency(String ticker) throws ApiException;
}
