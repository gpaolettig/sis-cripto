package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.key.HoldingKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHoldingDAO extends CrudRepository<Holding, HoldingKey> {
}
