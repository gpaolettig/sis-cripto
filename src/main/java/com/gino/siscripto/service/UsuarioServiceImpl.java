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
            return new ResponseEntity<>("El usuario con DNI "+user.getDni()+" ya existe",HttpStatus.BAD_REQUEST);
        }
        //Crear billetera (ya que si un usuario es creado se crea su billetera 1..*)
        List<Billetera>wallets= new ArrayList<>();
        Billetera wallet = new Billetera(); //el id lo genera jpa
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
    public Usuario modificarUsuario(Usuario usuario) {
        usuarioDAO.save(usuario);
        return usuario;
    }

    @Transactional
    @Override
    public void bajaUsuario(Usuario user) {
        usuarioDAO.delete(user);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listarUsuarios() {
        Iterable<Usuario> usuariosIterable = usuarioDAO.findAll();
        List<Usuario> listaUsuarios = new ArrayList<>();
        usuariosIterable.forEach(listaUsuarios::add);
        return listaUsuarios;
    }
}
