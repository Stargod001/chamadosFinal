package com.example.chamadosteste.modelo;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "log_atividade")
public class LogAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    private String acao;

    @Column(columnDefinition = "TEXT")
    private String detalhes;

    @Column(name = "data_hora", insertable = false, updatable = false)
    private Timestamp dataHora;

    // Getters e Setters

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }
}
