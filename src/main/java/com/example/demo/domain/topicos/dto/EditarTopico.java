package com.example.demo.domain.topicos.dto;

import jakarta.validation.constraints.NotBlank;

public record EditarTopico(@NotBlank String titulo, @NotBlank String mensagen) {}
