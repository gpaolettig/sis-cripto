package com.gino.siscripto.service;

import com.gino.siscripto.exceptions.*;
import com.gino.siscripto.model.entity.Currency;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.model.key.HoldingKey;
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
        //verificamos que exista la wallet en la bd
        if (!walletService.walletExist(holding.getWallet_id())) {
            throw new WalletDoesNotExist(holding.getWallet_id());
        }
        //verficamos que exista la currency en la bd
        if (!currencyService.currencyExist(holding.getCurrency_ticker())) {
            throw new CurrencyDoesNotExist(holding.getCurrency_ticker());
        }
        /*Como ticker es unique, al insertar una entidad que ya existe en la tabla holding
        es decir, misma billetera, mismo ticker, distinto amount Spring Data JPA
        lo actualizará, hará match con la clave de holding
        * */
        return iHoldingDAO.save(holding);
    }

    @Override
    public Holding deleteHolding(HoldingKey key) throws ApiException {
        Optional<Holding> holding = iHoldingDAO.findById(key);
        if(holding.isPresent()){
            iHoldingDAO.delete(holding.get());
            return holding.get();
        }
        throw new HoldingDoesNotExist();
    }

    @Override
    public Holding updateHolding(Holding holdingrequest, HoldingKey key) throws ApiException {
        Optional<Holding> holding = iHoldingDAO.findById(key);
        if(holding.isPresent()){
            holding.get().setId(holdingrequest.getId());
            holding.get().setCurrency_ticker(holdingrequest.getCurrency_ticker());
            holding.get().setAmount(holdingrequest.getAmount());
            iHoldingDAO.save(holding.get());
        }
        throw new HoldingDoesNotExist();
    }
}
