package com.example.demo.entitys.mensagens;

import com.example.demo.entitys.user.Usuario;
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
    Usuario autor;

    String conteudo;

    @Temporal(TemporalType.DATE)
    Date criacao;

    protected Mensagem() {}

    public Mensagem(Mensagem pai, Usuario autor, String conteudo) {
        this.pai = pai;
        this.autor = autor;
        this.conteudo = conteudo;
        this.criacao = new Date();
    }

    public Mensagem(Usuario autor, String conteudo){
        this(null,autor,conteudo);
    }
}
