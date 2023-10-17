package com.gino.siscripto.controller;

import com.gino.siscripto.dto.CreateUsuarioDTO;
import com.gino.siscripto.model.entity.Billetera;
import com.gino.siscripto.model.entity.Usuario;
import com.gino.siscripto.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Qualifier("usuarioServiceImpl")
    @Autowired
    private IUsuarioService userService;
    //Alta listo con DTO
   @PostMapping("/usuarios/crear")
    public ResponseEntity<?> create(@RequestBody CreateUsuarioDTO createUsuarioDTO){
      return userService.altaUsuario(createUsuarioDTO);
    }
    @GetMapping("/usuarios")
    public ResponseEntity<?> read(){
       return userService.listarUsuarios();

    }
    @GetMapping("/usuarios/{dni}")
    public Usuario read(@PathVariable String dni){
        return  userService.localizarUsuario(dni);
    }

    @PutMapping("/usuarios/update")
    public ResponseEntity<?> update(@RequestBody Usuario user){
        if(userService.localizarUsuario(user.getDni())!=null){
            userService.modificarUsuario(user);
            return ResponseEntity.ok().body("El usuario con DNI "+user.getDni()+" Ha sido actualizado");
        }
        return ResponseEntity.badRequest().body("El usuario a actualizar no existe.");

   }
   @DeleteMapping("/usuarios/delete/{dni}")
   public ResponseEntity<?> delete(@PathVariable String dni){
       //Verifico si el usuario existe en la BD deberia hacerlo el servicio y que retorne un respentity
       if(userService.localizarUsuario(dni)!=null){
           userService.bajaUsuario(userService.localizarUsuario(dni));
           return ResponseEntity.ok().body("El usuario con DNI "+dni+" Ha sido eliminado");
       }
       return ResponseEntity.badRequest().body("El usuario con DNI "+dni +" no existe.");

   }


}
