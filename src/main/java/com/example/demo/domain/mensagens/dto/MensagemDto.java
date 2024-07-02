package com.example.demo.domain.mensagens.dto;

import com.example.demo.domain.mensagens.Mensagem;

public record MensagemDto(String authorname, String mensagem) {
    public MensagemDto(Mensagem m) {
        this(m.getAutor().getNome(), m.getConteudo());
    }
}