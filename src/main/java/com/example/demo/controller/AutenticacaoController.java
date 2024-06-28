package com.example.demo.controller;

import com.example.demo.entitys.user.Login;
import com.example.demo.entitys.user.Usuario;
import com.example.demo.entitys.user.UsuarioCadastroDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.Token;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private Token tok;

    @Autowired
    private UserRepository repo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Login login) {
        var a = new UsernamePasswordAuthenticationToken(login.login(), login.senha());
        var t = manager.authenticate(a);

        return ResponseEntity.ok(String.format("{\"token\":\"%s\"}", tok.genToken((Usuario) t.getPrincipal())));
    }

    @PostMapping("/cadastro")
    @Transactional
    public void cadastro(@RequestBody @Valid UsuarioCadastroDto user) {
        repo.save(new Usuario(user));
    }
}