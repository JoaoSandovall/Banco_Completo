package com.example.bancofuncional.model;
public class CartaoCredito {
    private final String numero;
    private final String titular;
    private final String validade;
    private final double limiteTotal;
    private double faturaAtual;

    public CartaoCredito(String numero, String titular, String validade, double limite) {
        this.numero = numero;
        this.titular = titular;
        this.validade = validade;
        this.limiteTotal = limite;
        this.faturaAtual = 0;
    }

    public double getLimiteDisponivel() {
        return limiteTotal - faturaAtual;
    }

    public boolean realizarCompra(double valor, String estabelecimento) {
        if (valor > getLimiteDisponivel()) {
            System.out.println("Erro: Limite insuficiente no cartão de crédito.");
            return false;
        }
        this.faturaAtual += valor;
        System.out.printf("Compra de R$ %.2f em '%s' aprovada.%n", valor, estabelecimento);
        return true;
    }

    // Getters
    public double getFaturaAtual() { return faturaAtual; }
    public double getLimiteTotal() { return limiteTotal; }
    public String getNumero() { return numero; }

    // CORRIGIDO: Adicionados getters para os campos não utilizados
    public String getTitular() {
        return titular;
    }

    public String getValidade() {
        return validade;
    }
}