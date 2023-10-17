package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateUsuarioDTO;
import com.gino.siscripto.model.entity.Billetera;
import com.gino.siscripto.model.entity.Usuario;
import com.gino.siscripto.repository.IUsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    //logica de negocio
    //Inyección de dependencia
    @Autowired
    private IUsuarioDAO usuarioDAO;
    @Transactional
    @Override
    public ResponseEntity<?> altaUsuario(CreateUsuarioDTO createUsuarioDTO) {
        //transformar el userDTO en user
        Usuario user = new Usuario();
        user.setDni(createUsuarioDTO.getDni());
        user.setNombre(createUsuarioDTO.getNombre());
        user.setApellido(createUsuarioDTO.getApellido());
        user.setSexo(createUsuarioDTO.getSexo());
        user.setEmail(createUsuarioDTO.getEmail());
        user.setTelefono(createUsuarioDTO.getTelefono());

        //Verifico si el usuario existe en la BD
        if(localizarUsuario(user.getDni()) != null){
            return new ResponseEntity<>("El usuario con DNI "+user.getDni()+" ya existe",HttpStatus.CONFLICT);
        }
        //Crear billetera (ya que si un usuario es creado se crea su billetera 1..*)
        List<Billetera>wallets= new ArrayList<>();
        Billetera wallet = new Billetera(); //el id lo genera JPA
        wallet.setSaldo((float)0.0);
        wallet.setDni_usuario(user.getDni());
        wallets.add(wallet);
        //asignar las wallets al user
        user.setBilleteras(wallets);
        //llamar un metodo del repositorio para guardarlo
        usuarioDAO.save(user);
        //crear una respuesta
        return new ResponseEntity<>("El usuario fue creado con éxito",HttpStatus.CREATED);

    }
    @Transactional(readOnly = true)
    @Override
    public Usuario localizarUsuario(String dni) {
        return usuarioDAO.findById(dni).orElse(null);
    }
    @Transactional
    @Override
    public ResponseEntity<?> modificarUsuario(CreateUsuarioDTO createUsuarioDTO) {
        /*
        Usando save con un objeto que ya tiene un identificador (PK) Spring Data JPA
        actualiza en vez de agregar.
        Si el objeto ya existe en la base de datos y tiene un ID válido,
        Spring Data JPA generará una sentencia SQL de actualización para modificar los atributos del objeto en la base de datos.
        Si el objeto pasado a save no tiene un ID (o su ID es nulo), Spring Data JPA lo considerará un nuevo objeto y generará
        una sentencia SQL de inserción para agregarlo como un nuevo registro en la base de datos.
        */

        // Verificar si el usuario existe en la BD
        Usuario usuarioExistente = localizarUsuario(createUsuarioDTO.getDni());
        if (usuarioExistente != null) {
            // Actualizar los atributos del usuarioExistente con los valores del DTO
            usuarioExistente.setNombre(createUsuarioDTO.getNombre());
            usuarioExistente.setApellido(createUsuarioDTO.getApellido());
            usuarioExistente.setSexo(createUsuarioDTO.getSexo());
            usuarioExistente.setEmail(createUsuarioDTO.getEmail());
            usuarioExistente.setTelefono(createUsuarioDTO.getTelefono());
            // Guardar el usuarioExistente actualizado en la base de datos
            usuarioDAO.save(usuarioExistente);
            return new ResponseEntity<>("El usuario fue actualizado con éxito", HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario no se encontró en la base de datos", HttpStatus.NOT_FOUND);
    }
    @Transactional
    @Override
    public void bajaUsuario(Usuario user) {
        usuarioDAO.delete(user);
    }


    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Iterable<Usuario> usuariosIterable = usuarioDAO.findAll();
        usuariosIterable.forEach(listaUsuarios::add);
        if (listaUsuarios.isEmpty()){
            return new ResponseEntity<>("No se encontraron usuarios" ,HttpStatus.OK);
        }
        return new ResponseEntity<>(listaUsuarios,HttpStatus.OK);
    }
}
