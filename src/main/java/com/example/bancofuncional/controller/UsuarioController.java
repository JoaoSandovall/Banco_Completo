package com.example.bancofuncional.controller;

import com.example.bancofuncional.model.Cliente; // Importamos Cliente
import com.example.bancofuncional.model.Usuario;
import com.example.bancofuncional.service.SistemaBancario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private SistemaBancario sistemaBancario;

    record CadastroRequest(String email, String cpf, String senha, String nomeCompleto) {}
    record LoginRequest(String cpf, String senha) {}

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody CadastroRequest request) {
        // CORRIGIDO: A variável agora é do tipo Cliente
        Cliente novoCliente = sistemaBancario.cadastrarClienteComConta(
            request.email(), 
            request.cpf(), 
            request.senha(),
            request.nomeCompleto()
        );

        if (novoCliente != null) {
            return ResponseEntity.ok("Cadastro realizado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest request) {
        Usuario usuario = sistemaBancario.autenticarUsuario(request.cpf(), request.senha());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        // Retornamos um 401 Unauthorized se o login falhar
        return ResponseEntity.status(401).body(null);
    }
}