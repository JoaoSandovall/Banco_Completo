package com.example.bancofuncional.controller;

import com.example.bancofuncional.model.Usuario;
import com.example.bancofuncional.service.SistemaBancario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private SistemaBancario sistemaBancario;

    // DTOs para receber os dados do formulário
    record CadastroRequest(String email, String cpf, String senha) {}
    record LoginRequest(String cpf, String senha) {}

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody CadastroRequest request) {
        // Nossas pistas de depuração:
        System.out.println("--- [CONTROLLER] Requisição /api/cadastro recebida! ---");
        System.out.println("Dados recebidos: Email=" + request.email() + ", CPF=" + request.cpf());

        if (sistemaBancario == null) {
            System.err.println("### ERRO GRAVE: A variável sistemaBancario está NULL! ###");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
        }

        System.out.println("--- [CONTROLLER] Chamando o sistemaBancario.cadastrarUsuario...");
        Usuario novoUsuario = sistemaBancario.cadastrarUsuario(request.email(), request.cpf(), request.senha());

        if (novoUsuario != null) {
            System.out.println("--- [CONTROLLER] Cadastro retornou sucesso.");
            return ResponseEntity.ok("Cadastro realizado com sucesso!");
        } else {
            System.out.println("--- [CONTROLLER] Cadastro retornou falha (usuário já existe).");
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest request) {
        // Você pode adicionar as pistas de depuração aqui também se precisar
        Usuario usuario = sistemaBancario.autenticarUsuario(request.cpf(), request.senha());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}