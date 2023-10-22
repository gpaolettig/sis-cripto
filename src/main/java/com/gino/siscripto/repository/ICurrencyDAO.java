package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyDAO extends CrudRepository<Currency,String> {
}
