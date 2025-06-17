package com.example.bancofuncional.service;

import com.example.bancofuncional.model.Conta;
import com.example.bancofuncional.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante!

@Service
public class OperacaoService {

    @Autowired
    private ContaRepository contaRepository;

    // Adicione também o TransacaoRepository quando criá-lo

    @Transactional // Garante que a operação inteira seja um sucesso ou uma falha (nunca pela metade)
    public void realizarTransferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {
        System.out.println("Iniciando transferência de " + valor + " da conta " + numeroContaOrigem + " para " + numeroContaDestino);

        // 1. Validar o valor
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        // 2. Encontrar as contas no banco de dados
        Conta contaOrigem = contaRepository.findByNumeroConta(numeroContaOrigem)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada."));
        
        Conta contaDestino = contaRepository.findByNumeroConta(numeroContaDestino)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada."));

        // 3. Verificar saldo na conta de origem
        if (contaOrigem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente na conta de origem.");
        }

        // 4. Realizar as operações de débito e crédito
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        // 5. Salvar as alterações no banco de dados
        // Como estamos dentro de um método @Transactional, o Spring/JPA salva automaticamente
        // contaRepository.save(contaOrigem);
        // contaRepository.save(contaDestino);

        // 6. Registrar a transação na tabela 'transacao' (passo futuro)
        // Transacao novaTransacao = new Transacao(...);
        // transacaoRepository.save(novaTransacao);
        
        System.out.println("Transferência concluída com sucesso!");
    }
}