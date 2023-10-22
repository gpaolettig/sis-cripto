package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.CreateUserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IUserService {
    User createUser(CreateUserDTO createUserDTO) throws ApiException;
    User getUser(String dni) throws ApiException;
    User updateUser(String dni, CreateUserDTO createUserDTO) throws ApiException;
    User deleteUser(String dni) throws ApiException;
    List<User> getAll() throws ApiException;
}
