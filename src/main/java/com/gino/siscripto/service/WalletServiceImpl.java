package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateWalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.repository.IUserDAO;
import com.gino.siscripto.repository.IWalletDAO;
import com.gino.siscripto.service.interfaces.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements IWalletService {
    //hacer un metodo que busque una wallet y la retorne así no repetis codigo
    @Autowired
    private IWalletDAO iWalletDAO; //mal
    @Autowired
    private IUserDAO iUserDAO; //deberia usar el servicio y no el dao directamente
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
            //actualizo los atributos solo balance y dni
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

    @Override
    public Boolean walletExist(UUID id) {
        Optional<Wallet> wallet = iWalletDAO.findById(id);
        return wallet.isPresent();
    }

    @Override
    public Boolean updateBalance(UUID id, BigDecimal balance, int flag) {
        Optional<Wallet> wallet = iWalletDAO.findById(id);
        if (wallet.isPresent()) {
            if(flag==1) //Se agregaron criptos --> ingreso dinero
                wallet.get().setBalance(wallet.get().getBalance().add(balance));
            if(flag==0) //Se intercambio cripto --> egreso de dinero
                wallet.get().setBalance(wallet.get().getBalance().subtract(balance));
            return true;
        }
        return false;
    }


}
