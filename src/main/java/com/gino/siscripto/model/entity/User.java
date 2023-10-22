package com.gino.siscripto.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "dni_user")
    private String dni;
    @Column(name = "name_user")
    private String name;
    @Column(name = "surname_user")
    private String surname;
    @Column(name = "gender_user")
    private String gender;
    @Column(name = "email_user")
    private String email;
    @Column(name = "tel_user")
    private String tel;
    @OneToMany(mappedBy = "userDNI", cascade = CascadeType.ALL)
    private List<Wallet> wallets;
}
