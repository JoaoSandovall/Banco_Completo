package com.example.bancofuncional.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    @Column(name = "score_credito")
    private Double scoreCredito;

    // Relacionamento Um-para-Um: Um Cliente está ligado a um único Usuário
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @JsonManagedReference
    private Usuario usuario;

    // Relacionamento Um-para-Muitos: Um Cliente pode ter muitas Contas
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conta> contas = new ArrayList<>();

    // Getters e Setters
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setScoreCredito(Double score) { this.scoreCredito = score; }

    // Método para adicionar conta
    public void addConta(Conta conta) {
        if (this.contas == null) { this.contas = new ArrayList<>(); }
        this.contas.add(conta);
        conta.setCliente(this);
    }
}