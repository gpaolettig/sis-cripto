package com.gino.siscripto.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {
    private String type;
    private String origin_currency_ticker; //null --> deposito
    private String destination_currency_ticker;
    private UUID origin_wallet_id;//null --> deposito
    private UUID destination_wallet_id;
    private BigDecimal origin_amount; //null --> deposito
    //para intercambio, se determina en ejecución (equivalencia), para deposito es obligatorio
    private BigDecimal destination_amount;

}
