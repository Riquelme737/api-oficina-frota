package com.unifacs.ads.api_oficina_frota.model;

import com.unifacs.ads.api_oficina_frota.enums.StatusFerramenta;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_ferramenta")
public class FerramentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private StatusFerramenta status;

    public FerramentaModel() {
    }

    public FerramentaModel(UUID id, StatusFerramenta status, String nome) {
        this.id = id;
        this.status = status;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StatusFerramenta getStatus() {
        return status;
    }

    public void setStatus(StatusFerramenta status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
