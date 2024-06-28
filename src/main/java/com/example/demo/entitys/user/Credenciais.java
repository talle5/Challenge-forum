package com.example.demo.entitys.user;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Credenciais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Getter private String login;
    @Getter private String senha;

    protected Credenciais() {}

    public Credenciais(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
}
