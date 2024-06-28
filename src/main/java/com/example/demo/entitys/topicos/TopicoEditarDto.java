package com.example.demo.entitys.topicos;

import jakarta.validation.constraints.NotBlank;

public record TopicoEditarDto(
        @NotBlank String titulo,
        @NotBlank String mensagen
) {}
