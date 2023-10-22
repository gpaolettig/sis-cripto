package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.repository.IUserDAO;
import com.gino.siscripto.repository.IWalletDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            wallet.setUserDNI(createWalletDTO.getDni());
            wallet.setBalance(createWalletDTO.getBalance());
            // Guardar la billetera en la base de datos
            return iWalletDAO.save(wallet);
        } else {
            throw new UserDoesNotExists(createWalletDTO.getDni());
        }

    }

    @Override
    public Wallet getWallet(UUID id) throws ApiException{
        Optional<Wallet> wallet = iWalletDAO.findById(id);
        if(wallet.isPresent()){
           return wallet.get();
        }
        throw new WalletDoesNotExist(id);
    }

    @Override
    public List<Wallet> getAll(String dni_user) throws ApiException {
        Optional<User> user = iUserDAO.findById(dni_user);
        if(user.isPresent()){
            return user.get().getWallets();
        }
        throw new UserDoesNotExists(dni_user);
    }

    @Override
    public Wallet updateWallet(UUID id, CreateWalletDTO createWalletDTO) throws ApiException {
        Optional<Wallet> wallet = iWalletDAO.findById(id);
        if(wallet.isPresent()){
            //actualizo los atributos
            wallet.get().setBalance(createWalletDTO.getBalance());
            wallet.get().setUserDNI(createWalletDTO.getDni());
            //actualizar en la bd
            iWalletDAO.save(wallet.get());
            return wallet.get();
        }
        throw new WalletDoesNotExist(id);
    }

    @Override
    public Wallet deleteWallet(UUID id) throws ApiException {
        Optional<Wallet> wallet = iWalletDAO.findById(id);
        if(wallet.isPresent()){
            iWalletDAO.delete(wallet.get());
            return  wallet.get();
        }
        throw new WalletDoesNotExist(id);
    }
}
