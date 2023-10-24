package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateTransactionDTO;
import com.gino.siscripto.dto.TransactionSuccesfullyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.CurrencyDoesNotExist;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.Transaction;
import com.gino.siscripto.repository.ITransactionDAO;
import com.gino.siscripto.service.interfaces.ICurrencyService;
import com.gino.siscripto.service.interfaces.IHoldingService;
import com.gino.siscripto.service.interfaces.ITransactionService;
import com.gino.siscripto.service.interfaces.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    ITransactionDAO transactionDAO;
    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private IWalletService walletService;
    @Autowired
    private IHoldingService holdingService;
    @Transactional
    @Override
    public TransactionSuccesfullyDTO createTransaction(CreateTransactionDTO transactiondto) throws ApiException {
        //pasar de dto a obj
        Transaction transaction = new Transaction();
        transaction.setDate_transaction(getDateNow());
        transaction.setType(transactiondto.getType());
        transaction.setOrigin_currency_ticker(transactiondto.getOrigin_currency_ticker());
        transaction.setDestination_currency_ticker(transactiondto.getDestination_currency_ticker());
        transaction.setOrigin_wallet_id(transactiondto.getOrigin_wallet_id());
        transaction.setDestination_wallet_id(transactiondto.getDestination_wallet_id());
        transaction.setOrigin_amount(transactiondto.getOrigin_amount());
        checkCurrenciesExistence(transaction); //lanza posible exception
        checkWalletsExistence(transaction); //lanza posible exception

        //checkear que no sean ambas cripto igual, lanzar una exception personalizada
        if(transaction.getType().equals("Intercambio")){
            // checkear que tengan los fondos suficientes

            //Calcular la cantidad de criptomoneda de destino que recibira el usuario de origen
            BigDecimal priceCurrencyO = currencyService.getPrice(transactiondto.getOrigin_currency_ticker());
            BigDecimal priceCurrencyD = currencyService.getPrice(transactiondto.getDestination_currency_ticker());
            BigDecimal origin_amount = transaction.getOrigin_amount();
            BigDecimal destination_amount = (origin_amount.multiply(priceCurrencyO)).divide(priceCurrencyD, RoundingMode.UNNECESSARY);
            transaction.setDestination_amount(destination_amount);
            //reflejar el intercambio en holding
            Holding holding_walletO = new Holding(null,transaction.getOrigin_wallet_id(), transactiondto.getDestination_currency_ticker(), destination_amount);
            Holding holding_walletD = new Holding(null,transaction.getDestination_wallet_id(), transactiondto.getOrigin_currency_ticker(),origin_amount);
            holdingService.createHolding(holding_walletO);
            holdingService.createHolding(holding_walletD);

            Transaction transactionresponse = transactionDAO.save(transaction);

            return  new TransactionSuccesfullyDTO(transactionresponse.getIdtransaction(),transactionresponse.getDate_transaction(),transactionresponse.getType(),transactionresponse.getOrigin_wallet_id(),transactionresponse.getDestination_wallet_id());
        }
            return new TransactionSuccesfullyDTO();

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
    private Timestamp getDateNow(){
        Calendar calendar = Calendar.getInstance();
        // Convierte el Calendar en un objeto Date
        Date fechaHoraActual = calendar.getTime();
        // Crea un objeto Timestamp a partir de la fecha y hora actual
        Timestamp timestamp = new Timestamp(fechaHoraActual.getTime());
        return timestamp;
    }

    }

