package com.gino.siscripto.controller;

import com.gino.siscripto.dto.request.CreateTransactionDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    ITransactionService transactionService;
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionDTO transactiondto) throws ApiException {
        return new ResponseEntity<>(transactionService.createTransaction(transactiondto), HttpStatus.OK);
    }
}
