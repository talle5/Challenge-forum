package com.example.demo.entitys.user;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank
        String login,
        @NotBlank
        String senha) {}
