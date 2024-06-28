package com.example.demo.entitys.mensagens;

public record MensagemRespostaDto(
        String authorname,
        String mensagem
) {
    public MensagemRespostaDto(Mensagem m) {
        this(m.getAutor().getNome(),m.conteudo);
    }
}