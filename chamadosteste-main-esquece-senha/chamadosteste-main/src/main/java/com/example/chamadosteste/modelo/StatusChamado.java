package com.example.chamadosteste.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "status_chamado")
public class StatusChamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Integer idStatus;

    private String nome;

    // Construtor padrão
    public StatusChamado() {
    }

    // Novo construtor adicionado
    public StatusChamado(Integer idStatus, String nome) {
        this.idStatus = idStatus;
        this.nome = nome;
    }

    // Getters e Setters (mantenha os existentes)
    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}