package com.unifacs.ads.api_oficina_frota.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_ordem_servico")
public class OrdemServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    public OrdemServicoModel() {
    }

    public OrdemServicoModel(UUID id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
