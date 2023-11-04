package com.gino.siscripto.repository;

import com.gino.siscripto.model.entity.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Repository
public interface IWalletDAO extends CrudRepository<Wallet, UUID> {
    @Query("SELECT w FROM Wallet w WHERE w.userDNI = :userDNI")
    List<Wallet> findAllWalletsForUser(@Param("userDNI") String userDNI);
    @Query("SELECT SUM(w.balance) FROM Wallet w WHERE w.userDNI = :userDNI ")
    BigDecimal getAllBalance(@Param("userDNI") String userDNI);
}
