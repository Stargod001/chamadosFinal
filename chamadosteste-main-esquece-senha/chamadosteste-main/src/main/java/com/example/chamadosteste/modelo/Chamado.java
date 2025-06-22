package com.example.chamadosteste.modelo;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chamado")
    private Integer idChamado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_sla", nullable = false)
    private PoliticaSla sla;

    private String assunto;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusChamado status;

    @ManyToOne
    @JoinColumn(name = "prioridade_id", nullable = false)
    private Prioridade prioridade;

    @Column(name = "data_abertura")
    private Timestamp dataAbertura;

    @ManyToOne
    @JoinColumn(name = "responsavel_id") // Mapeia para a coluna responsavel_id no banco de dados
    private Usuario responsavel;

    // Getters e Setters
    public Integer getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Integer idChamado) {
        this.idChamado = idChamado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PoliticaSla getSla() {
        return sla;
    }

    public void setSla(PoliticaSla sla) {
        this.sla = sla;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Timestamp getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Timestamp dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }
}
