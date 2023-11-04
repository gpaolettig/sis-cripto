package com.gino.siscripto.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSuccesfullyDTO {
    private long idtransaction;
    private Timestamp date_transaction;
    private String type;
    private UUID origin_wallet_id;
    private UUID destination_wallet_id;
    //private BigDecimal transaction_fee (comision)
    //private String dni_user_o
    //private String dni_user_d
}
