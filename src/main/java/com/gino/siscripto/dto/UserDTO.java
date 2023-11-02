package com.gino.siscripto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String dni;
    private String name;
    private String surname;
    private String gender;
    private String email;
    private String tel;
}
