package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.model.entity.Wallet;
import org.springframework.stereotype.Service;

public interface IWalletService {
    Wallet createWallet(CreateWalletDTO createWalletDTO) throws ApiException;
}
