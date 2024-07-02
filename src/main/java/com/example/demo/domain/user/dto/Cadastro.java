package com.example.demo.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record Cadastro(
        @NotBlank String nome,
        @NotBlank String user,
        @NotBlank String senha
) {}
