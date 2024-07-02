package com.example.demo.services;

import com.example.demo.domain.topicos.Topico;
import com.example.demo.domain.topicos.dto.TopicoDto;
import com.example.demo.domain.topicos.dto.EditarTopico;
import com.example.demo.repository.TopicosRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class ForumService {

    private final TopicosRepository repo;
    private final UserRepository urepo;

    Page<TopicoDto> topicos(Pageable pagina, Boolean parametos) {
        return ((parametos) ? repo.findAll(pagina) : repo.findAllByEstado(Topico.Estado.ATIVO, pagina)).map(TopicoDto::new);
    }


    Topico topicoById(Long id) {
        return repo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    Page<Topico> topicos(Pageable pagina, String assunto) {
        return repo.findAllByAssunto(assunto, pagina);
    }

    Page<Topico> searchPerData(Pageable pagina, List<Date> data) {
        return repo.findAllByDataCriacaoBetween(data.get(0), data.get(1), pagina);
    }

    @Transactional
    Topico novoTopico(Long userId, String titulo, String mensagem) {
        var user = urepo.getReferenceById(userId);
        var topico = new Topico(titulo, user, mensagem);
        repo.save(topico);
        return topico;
    }

    @Transactional
    void closeTopic(Long id) {
        var top = repo.getReferenceById(id);
        top.close();
    }

    @Transactional
    void responder(Long topicId, Long userId, String resposta) {
        var topic = repo.getReferenceById(topicId);
        if (topic.getEstado() == Topico.Estado.ATIVO) {
            topic.novaRespota(topic.getMensagem(), urepo.getReferenceById(userId), resposta);
        } else throw new RuntimeException("topico encerrado");
    }


    @Transactional
    void editarTopico(Long id, EditarTopico new_info) {
        var topico = repo.getReferenceById(id);
        topico.setTitulo(new_info.titulo());
        topico.getMensagem().setConteudo(new_info.mensagen());
    }

    @Transactional
    void deleteTopico(Long id) {
        repo.deleteById(id);
    }

    //    private Usuario getAuthenticadUser() {
    //        var name = SecurityContextHolder.getContext().getAuthentication().getName();
    //        return urepo.findByNome(name).get();
    //    }
}
