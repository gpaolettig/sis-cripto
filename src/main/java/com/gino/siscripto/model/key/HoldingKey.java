package com.gino.siscripto.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;
@Embeddable
public class HoldingKey implements Serializable {
    @Column(name = "Billetera_id")
    private UUID id_wallet;
    @Column(name = "Divisa_ticker")
    private String ticker_currency;
}
