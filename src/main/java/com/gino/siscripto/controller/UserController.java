package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CreateUserDTO;
import com.gino.siscripto.exceptions.ApiException;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Qualifier("userServiceImpl")
    @Autowired
    private IUserService userService;

   //Alta User con manejo de excepciones
    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@RequestBody CreateUserDTO createUserDTO) throws ApiException {
      User user = userService.altaUsuario(createUserDTO);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @GetMapping("/usuarios")
    public ResponseEntity<?> read(){
       return userService.listarUsuarios();
    }
    @PutMapping("/usuarios") //me pasan los datos que quiero modificar en el dto
    public ResponseEntity<?> update(@RequestBody CreateUserDTO createUserDTO){
        return userService.modificarUsuario(createUserDTO);

   }
   @DeleteMapping("/usuarios/{dni}")
   public ResponseEntity<?> delete(@PathVariable String dni){
       return userService.bajaUsuario(dni);

   }


}
