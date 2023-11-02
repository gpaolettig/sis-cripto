package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateTransactionDTO;
import com.gino.siscripto.dto.TransactionSuccesfullyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.CurrencyDoesNotExist;
import com.gino.siscripto.exceptions.NotEnoughFunds;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.Transaction;
import com.gino.siscripto.model.key.HoldingKey;
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
import java.util.UUID;

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
        Transaction transaction = dtoToTransaction(transactiondto);
        if (transaction.getType().equals("Intercambio")) {
            checkCurrenciesExistence(transaction);
            checkWalletsExistence(transaction);
            // Si la wallet de origen tiene la cantidad a intercambiar
            if (checkHolding(transaction.getOrigin_wallet_id(), transaction.getOrigin_currency_ticker(), transaction.getOrigin_amount())) {
                //Calcular la cantidad de criptomoneda de destino que recibira el usuario de origen
                BigDecimal priceCurrencyO = currencyService.getPrice(transactiondto.getOrigin_currency_ticker());
                BigDecimal priceCurrencyD = currencyService.getPrice(transactiondto.getDestination_currency_ticker());
                BigDecimal origin_amount = transaction.getOrigin_amount();
                BigDecimal destination_amount = (origin_amount.multiply(priceCurrencyO)).divide(priceCurrencyD, RoundingMode.UNNECESSARY);
                transaction.setDestination_amount(destination_amount);
                //si la wallet de destino posee la cantidad a intercambiar
                if (checkHolding(transaction.getDestination_wallet_id(), transaction.getDestination_currency_ticker(), transaction.getDestination_amount())) {
                    //Creamos las tenencias que van a recibir
                    HoldingKey holdingKeyO = new HoldingKey(transaction.getOrigin_wallet_id(), transaction.getDestination_currency_ticker());
                    HoldingKey holdingKeyD = new HoldingKey(transaction.getDestination_wallet_id(), transaction.getOrigin_currency_ticker());
                    Holding holding_walletO = new Holding(holdingKeyO);
                    Holding holding_walletD = new Holding(holdingKeyD);
                    holding_walletO.setAmount(destination_amount);
                    holding_walletD.setAmount(origin_amount);

                    //Reflejar en holding, verificamos si ambas wallet ya tenian esa cantidad a recibir o no
                    //Si ya tiene la cripto que va a recibir en su wallet
                    //Wallet Origen
                    if (holdingService.checkHolding(holdingKeyO))
                        holdingService.updateHolding(holding_walletO, holdingKeyO, 1);
                    else //no tiene esa cripto en su wallet
                        holdingService.createHolding(holding_walletO);

                    //Wallet destino
                    if (holdingService.checkHolding(holdingKeyD))
                        holdingService.updateHolding(holding_walletD, holdingKeyD, 1);
                    else //no tiene esa cripto en su wallet
                        holdingService.createHolding(holding_walletD);

                    //Eliminar las tenencias que intercambiaron

                    HoldingKey deleteHoldingKeyO = new HoldingKey(transaction.getOrigin_wallet_id(), transaction.getOrigin_currency_ticker());
                    HoldingKey deleteHoldingKeyD = new HoldingKey(transaction.getDestination_wallet_id(), transaction.getDestination_currency_ticker());
                    Holding deleteHoldingO = new Holding(deleteHoldingKeyO, transaction.getOrigin_amount());
                    Holding deleteHoldingD = new Holding(deleteHoldingKeyD, transaction.getDestination_amount());
                    holdingService.updateHolding(deleteHoldingO, deleteHoldingKeyO, 0);
                    holdingService.updateHolding(deleteHoldingD, deleteHoldingKeyD, 0);
                } else {
                    throw new NotEnoughFunds(transaction.getDestination_wallet_id());
                }
            } else {
                throw new NotEnoughFunds(transaction.getOrigin_wallet_id());
            }

        }
        if (transaction.getType().equals("Deposito")){
            HoldingKey key = new HoldingKey(transaction.getDestination_wallet_id(),transaction.getDestination_currency_ticker());
            Holding holding = new Holding(key,transaction.getDestination_amount());
            if (holdingService.checkHolding(key))
                holdingService.updateHolding(holding,key,1);
            holdingService.createHolding(holding);
        }
        Transaction transactionResponse = transactionDAO.save(transaction);
        return new TransactionSuccesfullyDTO(transactionResponse.getIdtransaction(), transactionResponse.getDate_transaction(), transactionResponse.getType(), transactionResponse.getOrigin_wallet_id(), transactionResponse.getDestination_wallet_id());

    }

    private void checkCurrenciesExistence(Transaction transaction) throws ApiException {
        Boolean booleanCurrencyO = currencyService.currencyExist(transaction.getOrigin_currency_ticker());
        Boolean booleanCurrencyD = currencyService.currencyExist(transaction.getDestination_currency_ticker());
        if (!(booleanCurrencyO) || !(booleanCurrencyD)) {
            if (!booleanCurrencyO)
                throw new CurrencyDoesNotExist(transaction.getOrigin_currency_ticker());
            throw new CurrencyDoesNotExist(transaction.getDestination_currency_ticker());
        }
    }

    private void checkWalletsExistence(Transaction transaction) throws ApiException {
        Boolean booleanWalletO = walletService.walletExist(transaction.getOrigin_wallet_id());
        Boolean booleanWalletD = walletService.walletExist(transaction.getDestination_wallet_id());
        if (!(booleanWalletO) || !(booleanWalletD)) {
            if (!booleanWalletO)
                throw new WalletDoesNotExist(transaction.getOrigin_wallet_id());
            throw new WalletDoesNotExist(transaction.getDestination_wallet_id());
        }

    }

    private Timestamp getDateNow() {
        Calendar calendar = Calendar.getInstance();
        // Convierte el Calendar en un objeto Date
        Date fechaHoraActual = calendar.getTime();
        // Crea un objeto Timestamp a partir de la fecha y hora actual
        Timestamp timestamp = new Timestamp(fechaHoraActual.getTime());
        return timestamp;
    }

    private Transaction dtoToTransaction(CreateTransactionDTO transactiondto) {
        Transaction transaction = new Transaction();
        transaction.setDate_transaction(getDateNow());
        transaction.setType(transactiondto.getType());
        transaction.setOrigin_currency_ticker(transactiondto.getOrigin_currency_ticker());
        transaction.setDestination_currency_ticker(transactiondto.getDestination_currency_ticker());
        transaction.setOrigin_wallet_id(transactiondto.getOrigin_wallet_id());
        transaction.setDestination_wallet_id(transactiondto.getDestination_wallet_id());
        transaction.setOrigin_amount(transactiondto.getOrigin_amount());
        return transaction;
    }

    private Boolean checkHolding(UUID idWallet, String ticker, BigDecimal amount) throws ApiException {
        HoldingKey holdingKey = new HoldingKey(idWallet, ticker);
        Holding requieredHolding = new Holding(holdingKey, amount);
        return holdingService.checkHoldingAmount(requieredHolding);
    }

}

