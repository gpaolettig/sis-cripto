package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.repository.IUserDAO;
import com.gino.siscripto.repository.IWalletDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements IWalletService{
    @Autowired
    private IWalletDAO iWalletDAO;
    @Autowired
    private IUserDAO iUserDAO;
    @Transactional
    @Override
    public Wallet createWallet(CreateWalletDTO createWalletDTO) throws ApiException {
        // Controlar que el usuario exista antes de crear la billetera
        if (iUserDAO.findById(createWalletDTO.getDni()).isPresent()) {
            // Transformar walletDTO en wallet
            Wallet wallet = new Wallet();
            // El ID se generará automáticamente
            wallet.setDni_usuario(createWalletDTO.getDni());
            wallet.setSaldo(createWalletDTO.getSaldo());
            // Guardar la billetera en la base de datos
            return iWalletDAO.save(wallet);
        } else {
            throw new UserDoesNotExists(createWalletDTO.getDni());
        }

    }
}
