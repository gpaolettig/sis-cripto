package com.gino.siscripto.service;

import com.gino.siscripto.dto.CurrencyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.CurrencyAlreadyExist;
import com.gino.siscripto.exceptions.CurrencyDoesNotExist;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.Currency;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.repository.ICurrencyDAO;
import com.gino.siscripto.service.interfaces.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CurrencyServiceImpl implements ICurrencyService {
    @Autowired
    ICurrencyDAO iCurrencyDAO;
    @Override
    public CurrencyDTO createCurrency(CurrencyDTO currencyDTO) throws ApiException {
        //transformar dto a currency
        Currency currency = new Currency();
        currency.setTicker(currencyDTO.getTicker());
        currency.setName(currencyDTO.getName());
        currency.setValue(currencyDTO.getValue());
        //verificar si ya existe la divisa
        if (iCurrencyDAO.findById(currencyDTO.getTicker()).isPresent()){
            throw new CurrencyAlreadyExist(currencyDTO.getTicker());
        }
        iCurrencyDAO.save(currency);
        return currencyDTO;
    }

    @Override
    public CurrencyDTO updateCurrency(String ticker,CurrencyDTO currencyDTO) throws ApiException {
        Optional<Currency> currency = iCurrencyDAO.findById(ticker);
        if(currency.isPresent()){
            //actualizo los atributos
            currency.get().setName(currencyDTO.getName());
            currency.get().setValue(currencyDTO.getValue());
            //actualizar en la bd
            iCurrencyDAO.save(currency.get());
            return currencyDTO;
        }
        throw new CurrencyDoesNotExist(ticker);
    }

    @Override
    public CurrencyDTO deleteCurrency(String ticker) throws ApiException {
        Optional<Currency> currency = iCurrencyDAO.findById(ticker);
        if(currency.isPresent()){
            iCurrencyDAO.delete(currency.get());
            return new CurrencyDTO(currency.get().getTicker(),currency.get().getName(),currency.get().getValue());
        }
        throw new CurrencyDoesNotExist(ticker);
    }

    @Override
    public Boolean currencyExist(String ticker) {
        Optional<Currency> currency = iCurrencyDAO.findById(ticker);
            if(currency.isPresent())
                return true;
            return false;

    }
}
