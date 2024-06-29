package com.example.demo.controller;

import com.example.demo.entitys.mensagens.MensagemRespostaDto;
import com.example.demo.entitys.topicos.Topico;
import com.example.demo.entitys.topicos.TopicoDto;
import com.example.demo.entitys.topicos.TopicoEditarDto;
import com.example.demo.entitys.user.Usuario;
import com.example.demo.repository.TopicosRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("topicos")
@AllArgsConstructor
public class Forum {

    private final TopicosRepository repo;
    private final UserRepository urepo;

    @GetMapping
    PagedModel<TopicoDto> topicos(Pageable pagina, @RequestParam(name = "all", defaultValue = "false") Boolean parametos) {
        return new PagedModel<>(((parametos) ? repo.findAll(pagina) : repo.findAllByEstado(Topico.Estado.ATIVO, pagina)).map(TopicoDto::new));
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
        return new PagedModel<>(repo.findAllByDataCriacaoBetween(data.get(0), data.get(1), pagina));
    }

    @PostMapping
    @Transactional
    void registerNewTopic(@RequestBody @Valid TopicoDto var1) {
        var user = SecurityContextHolder.getContext().getAuthentication();
        var a = urepo.findByLogin(user.getName()).get();
        repo.save(new Topico(var1.titulo(),a, var1.mensagem()));
    }

    @PostMapping("/{id}/close")
    @Transactional
    void closeTopic(@PathVariable Long id) {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = urepo.findByLogin(name).get();
        var top = repo.getReferenceById(id);
        if (user == top.getAutor()) {
            top.close();
        }
    }

    @PostMapping("/{id}/resposta")
    @Transactional
    void responder(@PathVariable Long id, @RequestBody MensagemRespostaDto res) {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = urepo.findByLogin(name).get();
        var topic = repo.getReferenceById(id);
        topic.novaRespota(topic.getMensagem(),user, res.mensagem());
    }

    @PatchMapping("/{id}")
    @Transactional
    void editarTopico(@PathVariable Long id, @RequestBody @Valid TopicoEditarDto new_info) {
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
