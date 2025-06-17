package com.example.bancofuncional.service;

import com.example.bancofuncional.model.Conta;
import com.example.bancofuncional.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// IMPORT CORRETO PARA JAVA 17+ E SPRING BOOT 3+
import jakarta.annotation.PostConstruct;

import java.util.Map;

@Service
public class SistemaBancario {

    private Map<String, Usuario> usuariosPorCpf;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        this.usuariosPorCpf = DatabaseService.carregarUsuarios();
    }

    public Usuario cadastrarUsuario(String email, String cpf, String senhaPura) {
    if (usuariosPorCpf.containsKey(cpf)) {
        return null;
    }

    String nomeTemporario = email.substring(0, email.indexOf('@'));
    String senhaHasheada = passwordEncoder.encode(senhaPura);
    
    Usuario novoUsuario = new Usuario(nomeTemporario, cpf, email, senhaHasheada);
    
    String numeroConta = "12345-6"; // Apenas um exemplo
    Conta novaConta = new Conta(numeroConta, novoUsuario, 0.0);
    novoUsuario.setConta(novaConta);

    usuariosPorCpf.put(cpf, novoUsuario);

    // ======== PISTAS ADICIONADAS AQUI ========
    System.out.println("--> [SistemaBancario] Tentando salvar usuário com CPF: " + cpf);
    DatabaseService.salvarUsuarios(usuariosPorCpf);
    System.out.println("--> [SistemaBancario] Chamada para salvar concluída.");
    // =========================================
    
    return novoUsuario;
}

    public Usuario autenticarUsuario(String cpf, String senhaPura) {
        Usuario usuario = usuariosPorCpf.get(cpf);
        if (usuario != null && passwordEncoder.matches(senhaPura, usuario.getHashSenha())) {
            return usuario;
        }
        return null;
    }
}