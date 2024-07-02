package com.example.demo.domain.topicos.dto;

import com.example.demo.domain.mensagens.dto.MensagemDto;
import com.example.demo.domain.topicos.Topico;

import java.util.List;

public record TopicoDto(
        String titulo,
        String autor,
        String mensagem,
        List<MensagemDto> respostas) {
    public TopicoDto(Topico t) {
        this(t.getTitulo(),
             t.getAutor().getNome(),
             t.getMensagem().getConteudo(),
             t.getRespostas().stream().map(MensagemDto::new).toList());
    }
}
