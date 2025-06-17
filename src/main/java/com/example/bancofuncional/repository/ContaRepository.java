package com.example.bancofuncional.repository;

import com.example.bancofuncional.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    
    // O Spring entende esse nome de método e cria a busca SQL por nós!
    Optional<Conta> findByNumeroConta(String numeroConta);
}