package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billetera")
public class Billetera {
    @Id
    @Column(name = "id_billetera")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //false: Cada instancia de Billetera debe estar asociada obligatoriamente a una instancia de Usuario
    @OnDelete(action = OnDeleteAction.CASCADE) //Si elimino un usuario, se eliminan sus billeteras asociadas
    private Usuario usuario;
    @Column(name = "saldo_billetera")
    private Float saldo;
}
