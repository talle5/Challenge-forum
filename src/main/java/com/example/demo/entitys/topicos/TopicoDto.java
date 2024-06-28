package com.example.demo.entitys.topicos;

import com.example.demo.entitys.mensagens.MensagemRespostaDto;

import java.util.List;

public record TopicoDto(
        String titulo,
        String autor,
        String mensagem,
        List<MensagemRespostaDto> respostas) {
    public TopicoDto(Topico t) {
        this(t.getTitulo(),
             t.getAutor().getNome(),
             t.getMensagem().getConteudo(),
             t.getRespostas().stream().map(MensagemRespostaDto::new).toList());
    }
}
