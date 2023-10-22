package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.service.interfaces.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping("/wallets/{id}")
    public ResponseEntity<?>getWallet(@PathVariable UUID id) throws ApiException{
        Wallet wallet = iWalletService.getWallet(id);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }
    @GetMapping("/wallets/user/{dni}")
    public ResponseEntity<?> getAll(@PathVariable String dni) throws ApiException{
        List<Wallet> wallets = iWalletService.getAll(dni);
        return new ResponseEntity<>(wallets,HttpStatus.OK);

    }
    @PutMapping("/wallets/{id}")
    public ResponseEntity<?>updateWallet(@PathVariable UUID id, @RequestBody CreateWalletDTO createWalletDTO)throws ApiException{
        Wallet wallet = iWalletService.updateWallet(id,createWalletDTO);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }
    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<?>deleteWallet(@PathVariable UUID id) throws ApiException{
        Wallet wallet = iWalletService.deleteWallet(id);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

}
