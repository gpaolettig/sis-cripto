package com.gino.siscripto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String sexo;
    private String email;
    private String telefono;
}
