package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario,String> {
}
