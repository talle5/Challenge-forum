package com.example.demo.controllers;

import com.example.demo.domain.topicos.Topico;
import com.example.demo.domain.topicos.dto.TopicoDto;
import com.example.demo.domain.topicos.dto.EditarTopico;
import com.example.demo.domain.user.Usuario;
import com.example.demo.repository.TopicosRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
        return repo.findById(id).orElseThrow(EntityNotFoundException::new);
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
    ResponseEntity<Topico> registerNewTopic(@RequestBody @Valid TopicoDto var1, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(var1.titulo(), getAuthenticadUser(), var1.mensagem());
        repo.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @PostMapping("/{id}/close")
    @Transactional
    void closeTopic(@PathVariable Long id) {
        var user = getAuthenticadUser();
        var top = repo.getReferenceById(id);
        if (user == top.getAutor()) {
            top.close();
        } else throw new RuntimeException();
    }

    @PostMapping("/{id}/resposta")
    @Transactional
    void responder(@PathVariable Long id, @RequestBody String res, JwtAuthenticationToken token) {
        var topic = repo.findById(id).get();
        if (topic.getEstado() == Topico.Estado.ATIVO) {
            topic.novaRespota(topic.getMensagem(), urepo.getReferenceById(Long.valueOf(token.getName())), res);
        } else throw new RuntimeException("topico encerrado");
    }

    @PatchMapping("/{id}")
    @Transactional
    void editarTopico(@PathVariable Long id, @RequestBody @Valid EditarTopico new_info) {
        var topico = repo.getReferenceById(id);
        topico.setTitulo(new_info.titulo());
        topico.getMensagem().setConteudo(new_info.mensagen());
    }

    @DeleteMapping("{id}")
    @Transactional
    void deleteTopico(@PathVariable Long id) {
        repo.deleteById(id);
    }

    private Usuario getAuthenticadUser() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        return urepo.findByNome(name).get();
    }
}
