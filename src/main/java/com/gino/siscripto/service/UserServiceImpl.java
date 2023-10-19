package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateUserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.exceptions.UserAlreadyExists;
import com.gino.siscripto.exceptions.UserDoesNotExists;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.repository.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    //logica de negocio

    //Inyección de dependencia
    @Autowired
    private IUserDAO usuarioDAO;
    @Transactional
    @Override
    public User createUser(CreateUserDTO createUserDTO) throws UserAlreadyExists {
        //transformar el userDTO en user
        User user = new User();
        user.setDni(createUserDTO.getDni());
        user.setNombre(createUserDTO.getNombre());
        user.setApellido(createUserDTO.getApellido());
        user.setSexo(createUserDTO.getSexo());
        user.setEmail(createUserDTO.getEmail());
        user.setTelefono(createUserDTO.getTelefono());

        //Verifico si el usuario existe en la BD
        if(usuarioDAO.findById(createUserDTO.getDni()).isPresent()){
            throw new UserAlreadyExists(createUserDTO.getDni());
        }
        //Crear billetera (ya que si un usuario es creado se crea su billetera 1..*)
        List<Wallet>wallets= new ArrayList<>();
        Wallet wallet = new Wallet(); //el id lo genera JPA
        wallet.setSaldo((float)0.0);
        wallet.setDni_usuario(user.getDni());
        wallets.add(wallet);
        //asignar las wallets al user
        user.setWallets(wallets);
        //llamar un metodo del repositorio para guardarlo
        usuarioDAO.save(user);
        return user;

    }
    @Transactional(readOnly = true)
    @Override
    public User getUser(String dni) throws UserDoesNotExists {
        Optional<User> user = usuarioDAO.findById(dni);
        if(user.isPresent()){
            return  user.get();
        }
        throw new UserDoesNotExists(dni);
    }

    @Transactional
    @Override
    public User updateUser(String dni,CreateUserDTO createUserDTO) throws ApiException {
        /*
        Usando save con un objeto que ya tiene un identificador (PK) Spring Data JPA
        actualiza en vez de agregar.
        Si el objeto ya existe en la base de datos y tiene un ID válido,
        Spring Data JPA generará una sentencia SQL de actualización para modificar los atributos del objeto en la base de datos.
        Si el objeto pasado a save no tiene un ID (o su ID es nulo), Spring Data JPA lo considerará un nuevo objeto y generará
        una sentencia SQL de inserción para agregarlo como un nuevo registro en la base de datos.
        */

        // Verificar si el usuario existe en la BD
        User userExistente = getUser(dni);
        if (userExistente != null) {
            // Actualizar los atributos del userExistente con los valores del DTO
            userExistente.setNombre(createUserDTO.getNombre());
            userExistente.setApellido(createUserDTO.getApellido());
            userExistente.setSexo(createUserDTO.getSexo());
            userExistente.setEmail(createUserDTO.getEmail());
            userExistente.setTelefono(createUserDTO.getTelefono());
            // Guardar el userExistente actualizado en la base de datos
            usuarioDAO.save(userExistente);
            return userExistente;
        }
        throw new UserDoesNotExists(dni);
    }
    @Transactional
    @Override
    public User deleteUser(String dni) throws ApiException {
        //falta confirmación de baja con auth
        // Verificar si el usuario existe en la BD
        User userExistente = getUser(dni);
        if(userExistente != null){
            usuarioDAO.delete(userExistente);
            return userExistente;
        }
        throw new UserDoesNotExists(dni);
    }


    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        List<User> listaUsers = new ArrayList<>();
        Iterable<User> usuariosIterable = usuarioDAO.findAll();
        usuariosIterable.forEach(listaUsers::add);
        return listaUsers;
    }
}
