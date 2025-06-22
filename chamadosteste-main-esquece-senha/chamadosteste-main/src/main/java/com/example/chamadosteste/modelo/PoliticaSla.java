package com.example.chamadosteste.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "politicas_sla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoliticaSla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sla")
    private int idSla;

    @Column(nullable = false, length = 50)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prioridade_id", nullable = false)
    private Prioridade prioridade;

    @Column(name = "tempo_resposta", nullable = false)
    private LocalTime tempoResposta;

    @Column(name = "tempo_resolucao", nullable = false)
    private LocalTime tempoResolucao;

    // Se você precisar de um construtor específico além do gerado pelo Lombok:
    public PoliticaSla(String nome, Prioridade prioridade, LocalTime tempoResposta, LocalTime tempoResolucao) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.tempoResposta = tempoResposta;
        this.tempoResolucao = tempoResolucao;
    }
}
