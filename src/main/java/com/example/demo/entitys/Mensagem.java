package com.example.demo.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Mensagem pai;

    @ManyToOne(cascade = CascadeType.ALL)
    Autor autor;

    String conteudo;

    Date criacao;

    protected Mensagem() {}

    public Mensagem(Mensagem pai, Autor autor, String conteudo) {
        this.pai = pai;
        this.autor = autor;
        this.conteudo = conteudo;
        this.criacao = new Date();
    }

    public Mensagem(Autor autor,String conteudo){
        this(null,autor,conteudo);
    }
}
