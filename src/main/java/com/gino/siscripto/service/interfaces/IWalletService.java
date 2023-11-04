package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.WalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IWalletService {
    WalletDTO createWallet(WalletDTO walletDTO) throws ApiException;
    WalletDTO getWallet(UUID id) throws ApiException;
    List<WalletDTO> getAll(String dni_user) throws ApiException;
    WalletDTO updateWallet(UUID id, WalletDTO walletDTO) throws ApiException;
    WalletDTO deleteWallet(UUID id) throws ApiException;
    Boolean walletExist(UUID id);
    Boolean updateBalance(UUID id, BigDecimal balance, int flag);
}
