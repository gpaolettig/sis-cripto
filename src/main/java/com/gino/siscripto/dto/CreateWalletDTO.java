package com.gino.siscripto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletDTO {
    private String dni;
    private BigDecimal balance;
}
