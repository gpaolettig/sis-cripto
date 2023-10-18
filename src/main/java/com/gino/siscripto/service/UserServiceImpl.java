package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateUserDTO;
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

@Service
public class UserServiceImpl implements IUserService {
    //logica de negocio
    //Inyección de dependencia
    @Autowired
    private IUserDAO usuarioDAO;
    @Transactional
    @Override
    public ResponseEntity<?> altaUsuario(CreateUserDTO createUserDTO) {
        //transformar el userDTO en user
        User user = new User();
        user.setDni(createUserDTO.getDni());
        user.setNombre(createUserDTO.getNombre());
        user.setApellido(createUserDTO.getApellido());
        user.setSexo(createUserDTO.getSexo());
        user.setEmail(createUserDTO.getEmail());
        user.setTelefono(createUserDTO.getTelefono());

        //Verifico si el usuario existe en la BD
        if(localizarUsuario(user.getDni()) != null){
            return new ResponseEntity<>("El usuario con DNI "+user.getDni()+" ya existe",HttpStatus.CONFLICT);
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
        //crear una respuesta
        return new ResponseEntity<>("El usuario fue creado con éxito",HttpStatus.CREATED);

    }
    @Transactional(readOnly = true)
    @Override
    public User localizarUsuario(String dni) {
        return usuarioDAO.findById(dni).orElse(null);
    }
    @Transactional
    @Override
    public ResponseEntity<?> modificarUsuario(CreateUserDTO createUserDTO) {
        /*
        Usando save con un objeto que ya tiene un identificador (PK) Spring Data JPA
        actualiza en vez de agregar.
        Si el objeto ya existe en la base de datos y tiene un ID válido,
        Spring Data JPA generará una sentencia SQL de actualización para modificar los atributos del objeto en la base de datos.
        Si el objeto pasado a save no tiene un ID (o su ID es nulo), Spring Data JPA lo considerará un nuevo objeto y generará
        una sentencia SQL de inserción para agregarlo como un nuevo registro en la base de datos.
        */

        // Verificar si el usuario existe en la BD
        User userExistente = localizarUsuario(createUserDTO.getDni());
        if (userExistente != null) {
            // Actualizar los atributos del userExistente con los valores del DTO
            userExistente.setNombre(createUserDTO.getNombre());
            userExistente.setApellido(createUserDTO.getApellido());
            userExistente.setSexo(createUserDTO.getSexo());
            userExistente.setEmail(createUserDTO.getEmail());
            userExistente.setTelefono(createUserDTO.getTelefono());
            // Guardar el userExistente actualizado en la base de datos
            usuarioDAO.save(userExistente);
            return new ResponseEntity<>("El usuario fue actualizado con éxito", HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario no se encontró en la base de datos", HttpStatus.NOT_FOUND);
    }
    @Transactional
    @Override
    public ResponseEntity<?> bajaUsuario(String dni) {
        //falta confirmación de baja con jwt
        // Verificar si el usuario existe en la BD
        User userExistente = localizarUsuario(dni);
        if(userExistente != null){
            usuarioDAO.delete(userExistente);
            return new ResponseEntity<>("El usuario con dni "+dni+" ha sido eliminado", HttpStatus.OK);
        }
        return  new ResponseEntity<>("User con dni"+dni+" no encontrado",HttpStatus.NOT_FOUND);
    }


    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> listarUsuarios() {
        List<User> listaUsers = new ArrayList<>();
        Iterable<User> usuariosIterable = usuarioDAO.findAll();
        usuariosIterable.forEach(listaUsers::add);
        if (listaUsers.isEmpty()){
            return new ResponseEntity<>("No se encontraron usuarios" ,HttpStatus.OK);
        }
        return new ResponseEntity<>(listaUsers,HttpStatus.OK);
    }
}
