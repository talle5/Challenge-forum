package com.example.demo.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank String login,
        @NotBlank String senha
) {}
