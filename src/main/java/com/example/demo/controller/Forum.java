package com.example.demo.controller;

import com.example.demo.entitys.Topico;
import com.example.demo.entitys.TopicoDto;
import com.example.demo.repository.TopicosRepository;
import com.example.demo.specification.TopicoSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("topicos")
public class Forum {

    @Autowired
    TopicosRepository repo;

    @GetMapping
    PagedModel<Topico> topicos(Pageable pagina, @RequestParam(name = "all", defaultValue = "false") Boolean parametos) {
        if (parametos) {
            return new PagedModel<>(repo.findAll(pagina));
        } else {
            return new PagedModel<>(repo.findAllByEstado(Topico.Estado.ATIVO, pagina));
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
    PagedModel<Topico> topicos(Pageable pagina, @RequestParam(name = "date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date... data) {
        return new PagedModel<>(repo.findAll(TopicoSpecification.searchQueryBuilder(data), pagina));
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

    @DeleteMapping("{id}")
    @Transactional
    void deleteTopivo(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
