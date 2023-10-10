package com.gino.siscripto.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @Column(name = "DNI_usuario")
    private String dni;
    @Column(name = "Nombre_usuario")
    private String nombre;
    @Column(name = "Apellido_usuario")
    private String apellido;
    @Column(name = "sexo_usuario")
    private String sexo;
    @Column(name = "email_usuario")
    private String email;
    @Column(name = "telefono_usuario")
    private String telefono;
}
