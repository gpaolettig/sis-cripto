package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CurrencyDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.service.interfaces.ICurrencyService;
import com.gino.siscripto.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CurrencyController {
    @Autowired
    private ICurrencyService iCurrencyService;

    @PostMapping("/currencies")
    public ResponseEntity<?> createCurrency(@RequestBody CurrencyDTO currencyDTO) throws ApiException {
        return new ResponseEntity<>(iCurrencyService.createCurrency(currencyDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/currencies/{ticker}")
    public ResponseEntity<?> deleteurrency(@PathVariable String ticker) throws ApiException {
        return new ResponseEntity<>(iCurrencyService.deleteCurrency(ticker), HttpStatus.OK);
    }
    @PutMapping("/currencies/{ticker}")
    public ResponseEntity<?> updateCurrency(@PathVariable String ticker,@RequestBody CurrencyDTO currencyDTO) throws ApiException {
        return new ResponseEntity<>(iCurrencyService.updateCurrency(ticker,currencyDTO), HttpStatus.OK);
    }
}