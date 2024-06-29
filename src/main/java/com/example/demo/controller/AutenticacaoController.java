package com.example.demo.controller;

import com.example.demo.entitys.user.Login;
import com.example.demo.entitys.user.Usuario;
import com.example.demo.entitys.user.UsuarioCadastroDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AutenticacaoController {

    private final JwtService tok;

    private final UserRepository repo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Login login) {
        var a = new UsernamePasswordAuthenticationToken(login.login(), login.senha());
        return ResponseEntity.ok(tokenFormater(tok.generateToken(a)));
    }

    @PostMapping("/cadastro")
    @Transactional
    public void cadastro(@RequestBody @Valid UsuarioCadastroDto user) {
        repo.save(new Usuario(user));
    }

    private String tokenFormater(String token) {
        return String.format("{\"token\":\"%s\"}", token);
    }
}