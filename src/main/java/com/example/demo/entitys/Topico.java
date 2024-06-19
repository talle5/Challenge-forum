package com.example.demo.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
public class Topico {

    public enum Estado {
        ATIVO, ENCERRRADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    @OneToOne(cascade = CascadeType.ALL)
    private Mensagem mensagem;
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    @Enumerated
    private Estado estado;
    @OneToMany
    private List<Mensagem> respostas;

    public Topico(String titulo, Autor autor, String mensagem) {
        this.titulo = titulo;
        this.autor = autor;
        this.mensagem = new Mensagem(null, autor, mensagem);
        this.dataCriacao = new Date();
        this.estado = Estado.ATIVO;
        this.respostas = new LinkedList<>();
    }

    public Topico(TopicoDto dto) {
        this(dto.titulo(),new Autor(dto.autor()), dto.mensagem());
    }

    protected Topico() {}

    public void close() {
        this.estado = Estado.ENCERRRADO;
    }

    public void novaRespota(Mensagem pai, Autor autor, String conteudo) {
        respostas.add(new Mensagem(pai, autor, conteudo));
    }
}
