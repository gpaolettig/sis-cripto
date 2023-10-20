package com.gino.siscripto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletDTO {
    private String dniUser;
    private Float saldo;
}
