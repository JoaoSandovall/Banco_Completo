package com.example.bancofuncional.model;

public class Usuario {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String hashSenha;
    private Conta conta;

    public Usuario() {} 

    public Usuario(String nomeCompleto, String cpf, String email, String senhaHasheada) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.hashSenha = senhaHasheada;
    }

    public String getNomeCompleto() { return this.nomeCompleto; }
    public String getCpf() { return this.cpf; }
    public String getEmail() { return this.email; }
    public String getHashSenha() { return this.hashSenha; }
    public Conta getConta() { return this.conta; }
    public void setConta(Conta conta) { this.conta = conta; }
}