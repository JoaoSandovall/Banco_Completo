package com.example.bancofuncional.repository;

import com.example.bancofuncional.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring Data JPA cria magicamente a busca no banco de dados por nós!
    Optional<Usuario> findByCpf(String cpf);
}