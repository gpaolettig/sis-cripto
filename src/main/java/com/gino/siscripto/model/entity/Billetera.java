package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;


import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billetera")
public class Billetera {
    @Id
    @GeneratedValue
    @Column(name = "id_billetera", updatable = false, nullable = false)
    private int id;
    @Column(name="Usuario_DNI_usuario")
    private String dni_usuario; // La relacion es unidireccional de Usuario a billetera, pq si almacenamos el usuario tenemos serialización infinita
    @Column(name = "saldo_billetera")
    private Float saldo;
}
