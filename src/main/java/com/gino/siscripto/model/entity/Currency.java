package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @Column(name = "ticker_currency")
    private String ticker;
    @Column(name = "name_currency")
    private String name;
    @Column(name = "value_currency")
    private BigDecimal value; //en ARS
    //billetera has divisa
    @OneToMany(mappedBy = "currency_ticker")
    private List<Holding> holdingList;

    @OneToMany(mappedBy = "origin_currency_ticker", cascade = CascadeType.ALL)
    private List<Transaction> transactions_o;
    @OneToMany(mappedBy = "destination_currency_ticker", cascade = CascadeType.ALL)
    private List<Transaction> transactions_d;


}
