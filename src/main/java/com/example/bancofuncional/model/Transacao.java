package com.example.bancofuncional.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    public enum Tipo { PIX_ENVIADO, PIX_RECEBIDO, COMPRA_CREDITO, TRANSFERENCIA_RECEBIDA }

    private final double valor;
    private final String descricao;
    private final Tipo tipo;
    private final LocalDateTime data;

    public Transacao(double valor, String descricao, Tipo tipo) {
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
        this.data = LocalDateTime.now();
    }

    public double getValor() { return valor; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String sinal = (tipo == Tipo.PIX_RECEBIDO || tipo == Tipo.TRANSFERENCIA_RECEBIDA) ? "+" : "-";
        return String.format("[%s] %s %s R$ %.2f - %s",
                data.format(formatter), tipo, sinal, valor, descricao);
    }
}