package com.example.bancofuncional.service;

import com.example.bancofuncional.model.Cliente;
import com.example.bancofuncional.model.Conta;
import com.example.bancofuncional.model.Usuario;
import com.example.bancofuncional.repository.ClienteRepository;
import com.example.bancofuncional.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SistemaBancario {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // CORRIGIDO: O método agora retorna 'Cliente'
    public Cliente cadastrarClienteComConta(String email, String cpf, String senhaPura, String nomeCompleto) {
        if (usuarioRepository.findByCpf(cpf).isPresent()) {
            return null;
        }
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nomeCompleto);
        novoUsuario.setCpf(cpf);
        novoUsuario.setHashSenha(passwordEncoder.encode(senhaPura));
        
        Cliente novoCliente = new Cliente();
        novoCliente.setUsuario(novoUsuario);
        novoCliente.setScoreCredito(0.0);
        
        // Vamos usar um número de conta de exemplo
        String numeroContaExemplo = "CC-" + (10000 + (int)(Math.random() * 90000));
        Conta novaConta = new Conta(numeroContaExemplo, novoCliente, 0.0, Conta.TipoConta.CORRENTE);
        
        novoCliente.addConta(novaConta);

        return clienteRepository.save(novoCliente);
    }

    public Usuario autenticarUsuario(String cpf, String senhaPura) {
        return usuarioRepository.findByCpf(cpf)
                .filter(usuario -> passwordEncoder.matches(senhaPura, usuario.getHashSenha()))
                .orElse(null);
    }
}