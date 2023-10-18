package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDAO extends CrudRepository<User,String> {
}
