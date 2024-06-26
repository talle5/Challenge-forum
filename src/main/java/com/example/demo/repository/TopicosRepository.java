package com.example.demo.repository;

import com.example.demo.domain.topicos.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface TopicosRepository extends JpaRepository<Topico, Long>, JpaSpecificationExecutor<Topico> {

    @Override
    Optional<Topico> findById(Long aLong);

    @Query("select t from Topico t where t.estado = :estado")
    Page<Topico> findAllByEstado(Topico.Estado estado, Pageable pagina);

    @Query("select t from Topico t where t.mensagem.conteudo ilike %:assunto% or t.titulo ilike %:assunto%")
    Page<Topico> findAllByAssunto(String assunto, Pageable pagina);

    Page<Topico> findAllByDataCriacaoBetween(Date dataCriacao, Date dataCriacao2, Pageable pageable);
}
