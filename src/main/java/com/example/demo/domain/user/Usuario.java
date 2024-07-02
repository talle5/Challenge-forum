package com.example.demo.domain.user;

import com.example.demo.domain.mensagens.Mensagem;
import com.example.demo.domain.topicos.Topico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity @Getter @Setter
public class Usuario implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nome;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Topico> topicos;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Mensagem> mensagems;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Credenciais credenciais;

    @Temporal(TemporalType.DATE)
    Date ingreso;

    protected Usuario() {}

    public Usuario(String name,String nickname,String senha) {
        this.nome = name;
        topicos = new ArrayList<>();
        mensagems = new ArrayList<>();
        credenciais = new Credenciais(nickname, senha);
        ingreso = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("HOLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.credenciais.senha;
    }

    @Override
    public String getUsername() {
        return this.credenciais.login;
    }

    @Entity @Getter @Setter
    public static class Credenciais {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true)
        private String login;
        private String senha;

        protected Credenciais() {}

        public Credenciais(String login, String senha) {
            this.login = login;
            this.senha = senha;
        }
    }
}
