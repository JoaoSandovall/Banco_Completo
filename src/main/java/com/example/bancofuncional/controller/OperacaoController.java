package com.example.bancofuncional.controller;

import com.example.bancofuncional.service.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operacoes")
public class OperacaoController {

    @Autowired
    private OperacaoService operacaoService;

    // DTO para receber os dados do frontend
    record TransferenciaRequest(String contaOrigem, String contaDestino, double valor) {}

    @PostMapping("/transferencia")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaRequest request) {
        try {
            operacaoService.realizarTransferencia(
                request.contaOrigem(), 
                request.contaDestino(), 
                request.valor()
            );
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } catch (Exception e) {
            // Retorna a mensagem de erro da nossa lógica de negócios
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}