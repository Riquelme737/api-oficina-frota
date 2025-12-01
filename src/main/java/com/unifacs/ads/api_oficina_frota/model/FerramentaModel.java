package com.unifacs.ads.api_oficina_frota.model;

import com.unifacs.ads.api_oficina_frota.enums.StatusFerramenta;
import jakarta.persistence.*;

import java.util.List;
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

    @OneToMany(mappedBy = "ferramenta")
    private List<EmprestimoModel> emprestimos;

    public FerramentaModel() {
    }

    public FerramentaModel(UUID id, String nome, StatusFerramenta status, List<EmprestimoModel> emprestimos) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.emprestimos = emprestimos;
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

    public List<EmprestimoModel> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<EmprestimoModel> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
