package com.gino.siscripto.controller;

import com.gino.siscripto.model.entity.Usuario;
import com.gino.siscripto.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Qualifier("usuarioServiceImpl")
    @Autowired
    private IUsuarioService userService;
    //Alta
   @PostMapping("/crear")
    public Usuario create(@RequestBody Usuario user){
       /*quien consuma la api podra crear un usuario con o sin billetera.
       con billetera deberá mandar el objeto en json en el json.*/
       return userService.altaUsuario(user);
    }
    @GetMapping("/usuarios")
    public List<Usuario> read(){
       return  userService.listarUsuarios();
    }
    @GetMapping("/usuarios/{dni}")
    public Usuario read(@PathVariable String dni){
        return  userService.localizarUsuario(dni);
    }

    @PutMapping("/usuarios/update/{dni}")
    public Usuario update(@PathVariable String dni,@RequestBody Usuario usuario){
        //todo
        return  null;

   }

}
