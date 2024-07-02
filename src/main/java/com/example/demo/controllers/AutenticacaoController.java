package com.example.demo.controllers;

import com.example.demo.domain.user.Usuario;
import com.example.demo.domain.user.dto.Cadastro;
import com.example.demo.domain.user.dto.Login;
import com.example.demo.security.JwtService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AutenticacaoController {

    private final JwtService tok;

    private final AuthenticationManager manager;

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Login login) {
        var a = new UsernamePasswordAuthenticationToken(login.login(), login.senha());
        var user = (Usuario) manager.authenticate(a).getPrincipal();
        return ResponseEntity.ok(tokenFormater(tok.generateToken(user)));
    }

    @PostMapping("/cadastro")
    public void cadastro(@RequestBody @Valid Cadastro user) {
        service.newUser(user.nome(), user.user(), user.senha());
    }

    private String tokenFormater(String token) {
        return String.format("{\"token\":\"%s\"}", token);
    }
}