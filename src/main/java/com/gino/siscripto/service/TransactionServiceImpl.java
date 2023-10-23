package com.gino.siscripto.service;

import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.CurrencyDoesNotExist;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.Transaction;
import com.gino.siscripto.repository.ITransactionDAO;
import com.gino.siscripto.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    ITransactionDAO transactionDAO;
    @Autowired
    private CurrencyServiceImpl currencyService;
    @Autowired
    private WalletServiceImpl walletService;
    @Transactional
    @Override
    public Transaction createTransaction(Transaction transactionRequest) throws ApiException {
        checkCurrenciesExistence(transactionRequest); //lanza posbile exception
        checkWalletsExistence(transactionRequest); //lanza posbile exception
        //checkear que no sean ambas cripto igual, lanzar una exception personalizada
        //crear el objeto transaction, el id se genera automaticamente
        Calendar calendar = Calendar.getInstance();
        // Convierte el Calendar en un objeto Date
        Date fechaHoraActual = calendar.getTime();
        // Crea un objeto Timestamp a partir de la fecha y hora actual
        Timestamp timestamp = new Timestamp(fechaHoraActual.getTime());

        transactionRequest.setDate_transaction(timestamp);
        return transactionDAO.save(transactionRequest);
    }
    private void checkCurrenciesExistence(Transaction transaction) throws ApiException{
        Boolean booleanCurrencyO = currencyService.currencyExist(transaction.getOrigin_currency_ticker());
        Boolean booleanCurrencyD = currencyService.currencyExist(transaction.getDestination_currency_ticker());
        if (!(booleanCurrencyO) || !(booleanCurrencyD)) {
            if (!booleanCurrencyO)
                throw new CurrencyDoesNotExist(transaction.getOrigin_currency_ticker());
            throw new CurrencyDoesNotExist(transaction.getDestination_currency_ticker());
        }
    }
    private void checkWalletsExistence(Transaction transaction) throws ApiException{
        Boolean booleanWalletO = walletService.walletExist(transaction.getOrigin_wallet_id());
        Boolean booleanWalletD = walletService.walletExist(transaction.getDestination_wallet_id());
        if (!(booleanWalletO) || !(booleanWalletD)) {
            if (!booleanWalletO)
                throw new WalletDoesNotExist(transaction.getOrigin_wallet_id());
            throw new WalletDoesNotExist(transaction.getDestination_wallet_id());
        }

    }

    }
}
