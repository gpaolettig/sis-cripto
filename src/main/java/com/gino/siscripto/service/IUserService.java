package com.gino.siscripto.service;

import com.gino.siscripto.dto.CreateUserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User altaUsuario(CreateUserDTO createUserDTO) throws ApiException;
    User localizarUsuario(String dni);
    ResponseEntity<?> modificarUsuario(CreateUserDTO createUserDTO);
    ResponseEntity<?> bajaUsuario(String dni);
    ResponseEntity<?> listarUsuarios();
}
