package com.gino.siscripto.service;

import com.gino.siscripto.dto.WalletDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserDoesNotExist;
import com.gino.siscripto.exceptions.WalletDoesNotExist;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.repository.IWalletDAO;
import com.gino.siscripto.service.interfaces.IUserService;
import com.gino.siscripto.service.interfaces.IWalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements IWalletService {
    //hacer un metodo que busque una wallet y la retorne así no repetis codigo
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IWalletDAO walletDAO;
    @Autowired
    private IUserService userService;


    @Transactional
    @Override
    public WalletDTO createWallet(WalletDTO walletDTO) throws ApiException {
        // Controlar que el usuario exista antes de crear la billetera
        if (userService.getUserOptional(walletDTO.getDni()).isPresent()) {
            // Transformar walletDTO en wallet
            Wallet wallet = new Wallet();
            // El ID se genera automáticamente
            wallet.setUserDNI(walletDTO.getDni());
            wallet.setBalance(walletDTO.getBalance());
            // Guardar la billetera en la base de datos Y hacer mapeo a dto
            return modelMapper.map(walletDAO.save(wallet), WalletDTO.class);
        } else {
            throw new UserDoesNotExist(walletDTO.getDni());
        }

    }

    @Override
    public WalletDTO getWallet(UUID id) throws ApiException {
        Optional<Wallet> wallet = walletDAO.findById(id);
        if (wallet.isPresent()) {
            return modelMapper.map(wallet.get(), WalletDTO.class);
        }
        throw new WalletDoesNotExist(id);
    }

    @Override
    public List<WalletDTO> getAll(String dni_user) throws ApiException {
        Optional<User> user = userService.getUserOptional(dni_user);
        List<Wallet> wallets = new ArrayList<>();
        if (user.isPresent()) {
            //trae todas las wallets no las del user eh
            Iterable<Wallet> walletIterable = walletDAO.findAllWalletsForUser(dni_user);
            //recorro el iterable y agrego cada elemento a la lista
            walletIterable.forEach(wallets::add);
            //aplico un mapeo a cada billetera y luego recompilo los resultados a una lista
            return wallets.stream().map(wallet -> modelMapper.map(wallet, WalletDTO.class))
                    .collect(Collectors.toList());
        }
        throw new UserDoesNotExist(dni_user);
    }

    @Override
    public WalletDTO updateWallet(UUID id, WalletDTO walletDTO) throws ApiException {
        Optional<Wallet> wallet = walletDAO.findById(id);
        if (wallet.isPresent()) {
            //actualizo los atributos solo balance y dni
            wallet.get().setBalance(walletDTO.getBalance());
            wallet.get().setUserDNI(walletDTO.getDni());
            //actualizar en la bd
            //podria retornar el dto que viene por parametro y no volver a mapear
            return modelMapper.map(walletDAO.save(wallet.get()),WalletDTO.class);
        }
        throw new WalletDoesNotExist(id);
    }

    @Override
    public WalletDTO deleteWallet(UUID id) throws ApiException {
        Optional<Wallet> wallet = walletDAO.findById(id);
        if (wallet.isPresent()) {
            walletDAO.delete(wallet.get());
            return modelMapper.map(wallet.get(),WalletDTO.class);
        }
        throw new WalletDoesNotExist(id);
    }

    @Override
    public Boolean walletExist(UUID id) {
        Optional<Wallet> wallet = walletDAO.findById(id);
        return wallet.isPresent();
    }

    @Override
    public Boolean updateBalance(UUID id, BigDecimal balance, int flag) {
        Optional<Wallet> wallet = walletDAO.findById(id);
        if (wallet.isPresent()) {
            if (flag == 1) //Se agregaron criptos --> ingreso currency
                wallet.get().setBalance(wallet.get().getBalance().add(balance));
            if (flag == 0) //Se intercambio cripto --> egreso de dinero
                wallet.get().setBalance(wallet.get().getBalance().subtract(balance));
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal getBalance(UUID id) throws ApiException{
        Optional<Wallet> wallet = walletDAO.findById(id);
        if (wallet.isPresent()) {
            return wallet.get().getBalance();
        }
        throw new WalletDoesNotExist(id);
    }

    @Override
    public BigDecimal getAllBalance(String dni) throws ApiException {
        if(userService.getUserOptional(dni).isPresent()){
            return walletDAO.getAllBalance(dni);
        }
        throw new UserDoesNotExist(dni);
    }


}
