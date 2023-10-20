package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class WalletController {

    @Autowired
    IWalletService iWalletService;

    @PostMapping("/wallets")
    public ResponseEntity<?> createWallet(@RequestBody CreateWalletDTO createWalletDTO) throws ApiException {
        Wallet wallet = iWalletService.createWallet(createWalletDTO);
        return new ResponseEntity<>(wallet, HttpStatus.CREATED);
    }
}
