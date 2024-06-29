package com.example.demo.controller;

import com.example.demo.repository.MensagenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mensagen")
@AllArgsConstructor
public class MensagensContoller {

    private final MensagenRepository repo;

    @GetMapping("{str}")
    public PagedModel<?> searchMensagem(@PathVariable String str, Pageable page) {
        return new PagedModel<>(repo.findAllByConteudoContains(str,page));
    }

    @DeleteMapping("{id}")
    @Transactional
    void deleteMensagen(@PathVariable Long id) {
//         var mensagen = repo.getReferenceById(id);
//         mensagen.getAutor().getMensagems().remove(mensagen);
//         mensagen.setPai(null);
         repo.deleteById(id);
    }
}
