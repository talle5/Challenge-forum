package com.example.demo.controllers;

import com.example.demo.domain.user.dto.Login;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuariosControler {
    private final UserService service;

    @PatchMapping("/edit")
    ResponseEntity<?> editarUsuario(@RequestBody @Valid Login var1) {
        return ResponseEntity.ok().build();
    }
}
