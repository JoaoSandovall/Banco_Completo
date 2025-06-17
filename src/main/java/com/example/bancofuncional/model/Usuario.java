package com.example.bancofuncional.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
// Removida a herança daqui, pois estamos tratando como composição para alinhar com o banco de dados
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nome;
    private String cpf;
    private String hashSenha;
    // O campo tipoUsuario, email, etc. do seu SRS viriam aqui se necessário

    @OneToOne(mappedBy = "usuario")
    @JsonBackReference
    private Cliente cliente;

    // Construtor vazio
    public Usuario() {}

    // --- GETTERS E SETTERS ---
    public Long getId_usuario() { return id_usuario; }
    public void setId_usuario(Long id_usuario) { this.id_usuario = id_usuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getHashSenha() { return hashSenha; }
    public void setHashSenha(String hashSenha) { this.hashSenha = hashSenha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}