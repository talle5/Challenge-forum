package com.example.demo.specification;

import com.example.demo.domain.topicos.Topico;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class TopicoSpecification {
    public static Specification<Topico> searchQueryBuilder(Date... datas) {
        return (root, _, cb) -> {
            Predicate p = null;
            if (datas.length == 2) {
                p = cb.between(root.get("dataCriacao"), datas[0], datas[1]);
            }
            if (datas.length == 1) {
                p = cb.equal(root.get("dataCriacao"), datas[0]);
            }
            return p;
        };
    }

    public static Specification<Topico> inDateBetween(Date data1, Date data2) {
        return (root, _, cb) -> cb.between(root.get("dataCriacao"), data1, data2);
    }

    public static Specification<Topico> assunto(String assunto) {
        return (root, _, cb) -> cb.like(cb.lower(root.get("mensagem")), "%" + assunto.toLowerCase() + "%");
    }

}
