package com.gino.siscripto.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
@Embeddable
public class HoldingKey implements Serializable {
    @Column(name = "Wallet_id_wallet")
    private UUID id_wallet;
    @Column(name = "Currency_ticker_currency")
    private String ticker_currency;
    @Column(name="amount")
    private BigDecimal amount_currency;
}
