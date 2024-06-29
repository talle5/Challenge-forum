package com.example.demo.entitys.user;

import com.example.demo.entitys.mensagens.Mensagem;
import com.example.demo.entitys.topicos.Topico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario implements UserDetails {
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

//    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    Credenciais credenciais;
    String senha;

    @Temporal(TemporalType.DATE)
    Date ingreso;

    protected Usuario() {}

    public Usuario(String name,String nickname,String senha) {
        this.nome = name;
        topicos = new ArrayList<>();
        mensagems = new ArrayList<>();
        this.senha = senha;
//        credenciais = new Credenciais(nickname,senha);
        ingreso = new Date();
    }

    public Usuario(UsuarioCadastroDto user) {
        this(user.nome(),user.user(),user.senha());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("HOLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.nome;
    }
}
