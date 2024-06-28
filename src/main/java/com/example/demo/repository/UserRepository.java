package com.example.demo.repository;

import com.example.demo.entitys.user.Credenciais;
import com.example.demo.entitys.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Usuario,Long> {
    @Query("select u from Usuario u where u.nome = ?1")
    Usuario findByLogin(String c);
}
