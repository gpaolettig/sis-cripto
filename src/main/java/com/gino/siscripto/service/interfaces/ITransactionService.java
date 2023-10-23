package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Transaction;

public interface ITransactionService {
   //debería ser dto
    Transaction createTransaction(Transaction transaction) throws ApiException;
}
