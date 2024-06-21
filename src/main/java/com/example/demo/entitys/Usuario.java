package com.example.demo.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String nome;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Topico> topicos;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Mensagem> mensagems;

    @Temporal(TemporalType.DATE)
    Date ingreso;

    protected Usuario() {}

    public Usuario(String name) {
        this.nome = name;
        topicos = new ArrayList<>();
        mensagems = new ArrayList<>();
        ingreso = new Date();
    }
}
