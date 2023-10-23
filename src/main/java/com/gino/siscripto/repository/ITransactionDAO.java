package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionDAO extends CrudRepository<Transaction,Long> {
}
