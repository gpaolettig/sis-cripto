package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;


import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billetera")
public class Wallet {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id_billetera", updatable = false, nullable = false)
    private UUID id;
    @Column(name="Usuario_DNI_usuario")
    private String dni_usuario; // La relacion es unidireccional de User a billetera, pq si almacenamos el usuario tenemos serialización infinita
    @Column(name = "saldo_billetera")
    private Float saldo;
}
