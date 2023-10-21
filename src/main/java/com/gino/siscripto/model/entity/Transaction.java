package com.gino.siscripto.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaccion")
public class Transaction {
    @Id
    @Column(name="idTransaccion")
    private String idtransaction;
    @Column(name = "Fecha_hora_transaccion")
    private Timestamp date_transaction;
    @Column(name = "tipo_transaccion")
    private String type;

    @Column(name = "Divisa_Ticker_Origen")
    private String origin_currency_ticker;
    @Column(name = "Divisa_Ticker_Destino")
    private String destination_currency_ticker;

    @Column(name = "Billetera_id_Origen")
    private UUID origin_wallet_id;
    @Column(name = "Billetera_id_Destino")
    private UUID destination_wallet_id;




}
