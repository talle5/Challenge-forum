package com.example.demo.entitys.user;

import jakarta.validation.constraints.NotBlank;

public record UsuarioCadastroDto(
        @NotBlank String nome,
        @NotBlank String user,
        @NotBlank String senha
) {}
