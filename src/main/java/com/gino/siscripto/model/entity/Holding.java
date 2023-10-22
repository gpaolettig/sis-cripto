package com.gino.siscripto.model.entity;

import com.gino.siscripto.model.key.HoldingKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //tabla intermedia no necesita @table
public class Holding {
    @EmbeddedId
    private HoldingKey id;


    @MapsId("id_wallet")//mapea con Holdingkey
    @JoinColumn(name = "Billetera_id")
    private UUID wallet_id;


    @MapsId("ticker_currency") //mapea con Holdingkey
    @JoinColumn(name = "Divisa_ticker")
    private String currency_ticker;

    @Column(name = "valor_divisa")
    BigDecimal amount;
}
