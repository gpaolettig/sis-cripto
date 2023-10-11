package com.gino.siscripto.service;

import com.gino.siscripto.model.entity.Usuario;
import com.gino.siscripto.repository.IUsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Usuario altaUsuario(Usuario user) {
        return usuarioDAO.save(user);
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
