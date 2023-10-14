package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateUsuarioDTO;
import com.gino.siscripto.model.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUsuarioService {
    ResponseEntity<?> altaUsuario(CreateUsuarioDTO createUsuarioDTO);
    Usuario localizarUsuario(String dni);
    Usuario modificarUsuario(Usuario usuario);
    void bajaUsuario(Usuario user);
    List<Usuario> listarUsuarios();
}
