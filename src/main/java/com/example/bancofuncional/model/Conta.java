package com.example.bancofuncional.model;

import java.util.ArrayList;
import java.util.List;

// CORRIGIDO: Removidos os imports inválidos que começavam com "main".
// Os imports de "java.util" são os corretos para esta classe.

public class Conta {
    private final String agencia = "0001";
    private String numero;
    private double saldo;
    private Usuario titular;
    private List<Transacao> extrato;
    private CartaoCredito cartaoCredito;

    public Conta() {
        // Construtor vazio para desserialização
    }

    public Conta(String numero, Usuario titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.extrato = new ArrayList<>();
        this.cartaoCredito = new CartaoCredito(".... .... .... 1234", titular.getNomeCompleto(), "03/2034", 102304.87);
    }

    public double getSaldo() {
        return saldo;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }
    
    public String getNumero() { 
        return numero; 
    }

    public String getAgencia() { 
        return agencia; 
    }

    public Usuario getTitular() {
        return titular;
    }

    public boolean sacar(double valor, String descricao, Transacao.Tipo tipo) {
        if (valor > this.saldo) {
            System.out.println("Saldo insuficiente para a operação.");
            return false;
        }
        this.saldo -= valor;
        this.extrato.add(new Transacao(valor, descricao, tipo));
        return true;
    }

    public void depositar(double valor, String descricao, Transacao.Tipo tipo) {
        this.saldo += valor;
        this.extrato.add(new Transacao(valor, descricao, tipo));
    }

    public void exibirExtrato() {
        System.out.println("\n--- EXTRATO DETALHADO ---");
        if (extrato.isEmpty()) {
            System.out.println("Nenhuma transação recente.");
        } else {
            for (Transacao t : this.extrato) {
                System.out.println(t);
            }
        }
        System.out.println("-------------------------");
        System.out.printf("SALDO ATUAL: R$ %.2f%n", this.saldo);
    }
}