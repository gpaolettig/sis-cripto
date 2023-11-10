package com.gino.siscripto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "dni")
    @Size(min = 8, max = 8,message = "El dni debe contener 8 dígitos.")
    private String dni;
    @NotBlank(message = "name")
    private String name;
    @NotBlank(message = "surname")
    private String surname;
    @NotBlank(message = "gendr")
    private String gender;
    @NotBlank(message = "email")
    @Email
    private String email;
    @Size(min = 10, max = 10,message = "El número de teléfono debe contener 10 dígitos.")
    private String tel;
}
