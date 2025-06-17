package com.example.bancofuncional.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_conta;

    private String numeroConta;
    private Double saldo;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;
    private Date dataAbertura;
    @Enumerated(EnumType.STRING)
    private StatusConta status;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    public Conta() {}

    public Conta(String numeroConta, Cliente cliente, Double saldo, TipoConta tipoConta) {
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.dataAbertura = new Date();
        this.status = StatusConta.ATIVA;
    }

    // --- GETTERS E SETTERS PARA TODOS OS CAMPOS ---
    public Long getId_conta() { return id_conta; }
    public void setId_conta(Long id_conta) { this.id_conta = id_conta; }
    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
    public Double getSaldo() { return saldo; } // Resolve o erro
    public void setSaldo(Double saldo) { this.saldo = saldo; }
    public TipoConta getTipoConta() { return tipoConta; }
    public void setTipoConta(TipoConta tipoConta) { this.tipoConta = tipoConta; }
    public Date getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(Date dataAbertura) { this.dataAbertura = dataAbertura; }
    public StatusConta getStatus() { return status; }
    public void setStatus(StatusConta status) { this.status = status; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public enum TipoConta { POUPANCA, CORRENTE, INVESTIMENTO }
    public enum StatusConta { ATIVA, ENCERRADA, BLOQUEADA }
}