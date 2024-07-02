package com.example.demo.domain.mensagens;

import com.example.demo.domain.user.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity @Getter @Setter
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Mensagem pai;

    @ManyToOne(cascade = CascadeType.ALL)
    Usuario autor;

    String conteudo;

    @Temporal(TemporalType.TIMESTAMP)
    Instant criacao;

    protected Mensagem() {}

    public Mensagem(Mensagem pai, Usuario autor, String conteudo) {
        this.pai = pai;
        this.autor = autor;
        this.conteudo = conteudo;
        this.criacao = Instant.now();
    }

    public Mensagem(Usuario autor, String conteudo){
        this(null,autor,conteudo);
    }
}
