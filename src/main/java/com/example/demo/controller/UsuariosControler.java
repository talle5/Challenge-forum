package com.example.demo.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuariosControler {
//    private final

    @PatchMapping("edit")
    void editarUsuario(@RequestBody @Valid String nome){

    }
}
