package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IWalletDAO extends CrudRepository<Wallet, UUID> {
}
