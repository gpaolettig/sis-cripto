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
@Table(name = "divisa")
public class Currency {
    @Id
    @Column(name = "Ticker_divisa")
    private String ticker;
    @Column(name = "Nombre_divisa")
    private String name;
    @Column(name = "valor_divisa")
    private BigDecimal value;
    //billetera has divisa
    @OneToMany(mappedBy = "currency_ticker")
    private List<Holding> holdingList;
}
