package com.gino.siscripto.dto;

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
    private String origin_currency_ticker;
    private String destination_currency_ticker;
    private UUID origin_wallet_id;
    private UUID destination_wallet_id;
    private BigDecimal origin_amount;
    //destino para los depositos
    //solo el monto de origen, un usario quiere intercambiar x btc por eth, el servicio calcula la cantidad de destino
}
