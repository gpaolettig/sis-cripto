package com.gino.siscripto.service;

import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.CurrencyDoesNotExist;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.Currency;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.repository.IHoldingDAO;
import com.gino.siscripto.repository.IWalletDAO;
import com.gino.siscripto.service.interfaces.IHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HoldingServiceImpl implements IHoldingService {
   @Autowired
    private IHoldingDAO iHoldingDAO;
   @Autowired
    private WalletServiceImpl walletService;
    @Autowired
    private CurrencyServiceImpl currencyService;

    @Override
    public Holding createHolding(Holding holding) throws ApiException {
        //verificamos que exista la wallet
        if (!walletService.walletExist(holding.getWallet_id())) {
            throw new WalletDoesNotExist(holding.getWallet_id());
        }
        //verficamos que exista la currency
        if (!currencyService.currencyExist(holding.getCurrency_ticker())) {
            throw new CurrencyDoesNotExist(holding.getCurrency_ticker());
        }
       return iHoldingDAO.save(holding);
    }
}
