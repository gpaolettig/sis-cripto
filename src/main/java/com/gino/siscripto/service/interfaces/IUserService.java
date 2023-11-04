package com.gino.siscripto.service.interfaces;

import com.gino.siscripto.dto.UserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    UserDTO createUser(UserDTO userDTO) throws ApiException;
    UserDTO getUser(String dni) throws ApiException;
    UserDTO updateUser(String dni, UserDTO userDTO) throws ApiException;
    UserDTO deleteUser(String dni) throws ApiException;
    List<UserDTO> getAll() throws ApiException;
    Optional<User> getUserOptional(String dni);
}
