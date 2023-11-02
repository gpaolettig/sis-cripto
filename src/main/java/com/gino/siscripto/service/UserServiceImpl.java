package com.gino.siscripto.service;

import com.gino.siscripto.dto.UserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserAlreadyExists;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.repository.IUserDAO;
import com.gino.siscripto.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDAO usuarioDAO;
    @Autowired //Spring da automáticamente una instancia de ModelMapper al campo modelMapper cuando se cree una instancia de UserServiceImpl
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) throws UserAlreadyExists {
        //transformar el userDTO en user
        User user = modelMapper.map(userDTO,User.class);

        //Verifico si el usuario existe en la BD
        if(usuarioDAO.findById(userDTO.getDni()).isPresent()){
            throw new UserAlreadyExists(userDTO.getDni());
        }
        //Crear billetera (ya que si un usuario es creado se crea su billetera 1..*)
        List<Wallet>wallets= new ArrayList<>();
        Wallet wallet = new Wallet(); //el id lo genera HibernateJPA
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUserDNI(user.getDni());
        //al crear una wallet no trae criptos ni transacciones, por lo tanto todas las list en null
        wallets.add(wallet);
        //asignar las wallets al user
        user.setWallets(wallets);
        //llamar un metodo del repositorio para guardarlo
        usuarioDAO.save(user);
        UserDTO dto =modelMapper.map(user,UserDTO.class);
        return dto;
    }
    @Transactional(readOnly = true)
    @Override
    public UserDTO getUser(String dni) throws UserDoesNotExists {
        Optional<User> user = usuarioDAO.findById(dni);
        if(user.isPresent()){
            return modelMapper.map(user.get(),UserDTO.class);
        }
        throw new UserDoesNotExists(dni);
    }

    @Transactional
    @Override
    public UserDTO updateUser(String dni, UserDTO userDTO) throws ApiException {
        /*
        Usando save con un objeto que ya tiene un identificador (PK) Spring Data JPA
        actualiza en vez de agregar.
        Si el objeto ya existe en la base de datos y tiene un ID válido,
        Spring Data JPA generará una sentencia SQL de actualización para modificar los atributos del objeto en la base de datos.
        Si el objeto pasado a save no tiene un ID (o su ID es nulo), Spring Data JPA lo considerará un nuevo objeto y generará
        una sentencia SQL de inserción para agregarlo como un nuevo registro en la base de datos.
        */

        // Verificar si el usuario existe en la BD
        Optional<User> user = usuarioDAO.findById(dni);
        if (user.isPresent()) {
            User userExistente=user.get();
            // Actualizar los atributos del userExistente con los valores del DTO
            userExistente.setName(userDTO.getName());
            userExistente.setSurname(userDTO.getSurname());
            userExistente.setGender(userDTO.getGender());
            userExistente.setEmail(userDTO.getEmail());
            userExistente.setTel(userDTO.getTel());
            // Guardar el userExistente actualizado en la base de datos
            usuarioDAO.save(userExistente);
            return modelMapper.map(userExistente,UserDTO.class);
        }
        throw new UserDoesNotExists(dni);
    }
    @Transactional
    @Override
    public UserDTO deleteUser(String dni) throws ApiException {
        //falta confirmación de baja con auth
        // Verificar si el usuario existe en la BD
        Optional<User> user = usuarioDAO.findById(dni);
        if(user.isPresent()){
            usuarioDAO.delete(user.get());
            return modelMapper.map(user.get(),UserDTO.class);
        }
        throw new UserDoesNotExists(dni);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> getAll() {
        List<User> users = new ArrayList<>();
        Iterable<User> usuariosIterable = usuarioDAO.findAll();
        usuariosIterable.forEach(users::add);
        //mapeo a list<UserDTO> con funcion lambda usando modelMapper
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
