package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.request.CreateTransactionDTO;
import com.gino.siscripto.dto.response.TransactionSuccesfullyDTO;
import com.gino.siscripto.exceptions.ApiException;

public interface ITransactionService {
   //debería ser dto
    TransactionSuccesfullyDTO createTransaction(CreateTransactionDTO transactiondto) throws ApiException;
}
