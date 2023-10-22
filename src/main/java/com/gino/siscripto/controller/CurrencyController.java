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
        CurrencyDTO currencyDTOResponse = iCurrencyService.createCurrency(currencyDTO);
        return new ResponseEntity<>(currencyDTOResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/currencies/{ticker}")
    public ResponseEntity<?> deleteurrency(@PathVariable String ticker) throws ApiException {
        CurrencyDTO currencyDTOResponse = iCurrencyService.deleteCurrency(ticker);
        return new ResponseEntity<>(currencyDTOResponse, HttpStatus.OK);
    }
    @PutMapping("/currencies/{ticker}")
    public ResponseEntity<?> updateCurrency(@PathVariable String ticker,@RequestBody CurrencyDTO currencyDTO) throws ApiException {
        CurrencyDTO currencyDTOResponse = iCurrencyService.updateCurrency(ticker,currencyDTO);
        return new ResponseEntity<>(currencyDTOResponse, HttpStatus.OK);
    }
}