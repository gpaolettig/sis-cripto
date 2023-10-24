package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.CreateTransactionDTO;
import com.gino.siscripto.dto.TransactionSuccesfullyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Transaction;

public interface ITransactionService {
   //debería ser dto
    TransactionSuccesfullyDTO createTransaction(CreateTransactionDTO transactiondto) throws ApiException;
}
