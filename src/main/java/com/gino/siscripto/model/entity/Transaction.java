package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_transaction")
    private long idtransaction;
    @Column(name = "datetime_transaction")
    private Timestamp date_transaction;
    @Column(name = "type_transaction")
    private String type;

    @Column(name = "Currency_ticker_currency_o")
    private String origin_currency_ticker;
    @Column(name = "Currency_ticker_currency_d")
    private String destination_currency_ticker;

    @Column(name = "Wallet_id_wallet_o")
    private UUID origin_wallet_id;
    @Column(name = "Wallet_id_wallet_d")
    private UUID destination_wallet_id;
    @Column(name = "amount_transaction_o")
    private BigDecimal origin_amount;
    @Column(name = "amount_transaction_d")
    private BigDecimal destination_amount;


}
