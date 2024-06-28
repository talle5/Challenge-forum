package com.example.demo.controller;

import com.example.demo.entitys.mensagens.MensagemRespostaDto;
import com.example.demo.entitys.topicos.Topico;
import com.example.demo.entitys.topicos.TopicoDto;
import com.example.demo.entitys.topicos.TopicoEditarDto;
import com.example.demo.entitys.user.Usuario;
import com.example.demo.repository.TopicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("topicos")
public class Forum {

    @Autowired
    TopicosRepository repo;

    @GetMapping
    PagedModel<TopicoDto> topicos(Pageable pagina, @RequestParam(name = "all", defaultValue = "false") Boolean parametos) {
        if (parametos) {
            return new PagedModel<>(repo.findAll(pagina).map(TopicoDto::new));
        } else {
            return new PagedModel<>(repo.findAllByEstado(Topico.Estado.ATIVO, pagina).map(TopicoDto::new));
        }
    }

    @GetMapping("{id}")
    Topico topicoById(@PathVariable Long id) {
        return repo.findTopicoById(id);
    }

    @GetMapping("/search/{assunto}")
    PagedModel<Topico> topicos(Pageable pagina, @PathVariable String assunto) {
        return new PagedModel<>(repo.findAllByAssunto(assunto, pagina));
    }

    @GetMapping("/search")
    PagedModel<Topico> searchPerData(Pageable pagina, @RequestParam(name = "data") @DateTimeFormat(pattern = "dd-MM-yyyy") List<Date> data) {
        return new PagedModel<>(repo.findAllByDataCriacaoBetween(data.get(0),data.get(1), pagina));
    }

    @PostMapping
    @Transactional
    void registerNewTopic(@RequestBody @Valid TopicoDto var1) {
        repo.save(new Topico(var1));
    }

    @PostMapping("/{id}/close")
    @Transactional
    void closeTopic(@PathVariable Long id) {
        repo.getReferenceById(id).close();
    }

    @PostMapping("/{id}/resposta")
    @Transactional
    void responder(@PathVariable Long id,@RequestBody MensagemRespostaDto res) {
        var topic = repo.getReferenceById(id);
        topic.novaRespota(topic.getMensagem(),new Usuario(res.authorname()),res.mensagem());
    }

    @PatchMapping("/{id}")
    @Transactional
    void editarTopico(@PathVariable Long id,@RequestBody @Valid TopicoEditarDto new_info) {
        var topico = repo.getReferenceById(id);
        topico.setTitulo(new_info.titulo());
        topico.getMensagem().setConteudo(new_info.mensagen());
    }

    @DeleteMapping("{id}")
    @Transactional
    void deleteTopico(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
