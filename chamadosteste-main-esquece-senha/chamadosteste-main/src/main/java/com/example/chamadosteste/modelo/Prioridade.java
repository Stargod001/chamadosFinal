package com.example.chamadosteste.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "prioridades") // ou ajuste o nome conforme sua tabela real
public class Prioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridade")
    private int idPrioridade;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Prioridade() {
    }

    public Prioridade(int idPrioridade, String nome) {
        this.idPrioridade = idPrioridade;
        this.nome = nome;
    }

    public int getIdPrioridade() {
        return idPrioridade;
    }

    public void setIdPrioridade(int idPrioridade) {
        this.idPrioridade = idPrioridade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
