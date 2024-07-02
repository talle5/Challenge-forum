package com.example.demo.services;

import com.example.demo.domain.user.Usuario;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public void newUser(String nome, String userName, String password) {
        repository.save(new Usuario(nome, userName, encoder.encode(password)));
    }

    @Transactional
    public void mudarSenha(Long id, String senha) {
        var user = repository.getReferenceById(id);
        user.getCredenciais().setSenha(encoder.encode(senha));
    }

    @Transactional
    public void mudarNome(Long id, String nome) {
        var user = repository.getReferenceById(id);
        user.setNome(nome);
    }

    @Transactional
    public void mudarNick(Long id, String nick) {
        var user = repository.getReferenceById(id);
        user.getCredenciais().setLogin(nick);
    }
}
