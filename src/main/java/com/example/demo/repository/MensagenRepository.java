package com.example.demo.repository;

import com.example.demo.entitys.mensagens.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MensagenRepository extends JpaRepository<Mensagem, Long> {
    Page<Mensagem> findAllByConteudoContains(String str, Pageable page);

    @Modifying
    @Query(value = "delete from topicos_respostas where respostas_id = ?1 ;update mensagem set autor_id = null where id = ?1;delete from mensagem where id = ?1;", nativeQuery = true)
    void deleteById(Long id);
}
