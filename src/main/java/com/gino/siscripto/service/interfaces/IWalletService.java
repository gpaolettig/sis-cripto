package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IWalletService {
    Wallet createWallet(CreateWalletDTO createWalletDTO) throws ApiException;
    Wallet getWallet(UUID id) throws ApiException;
    List<Wallet> getAll(String dni_user) throws ApiException;
    Wallet updateWallet(UUID id,CreateWalletDTO createWalletDTO) throws ApiException;
    Wallet deleteWallet(UUID id) throws ApiException;
    Boolean walletExist(UUID id);
    Boolean UpdateBalance(UUID id, BigDecimal balance);
}
