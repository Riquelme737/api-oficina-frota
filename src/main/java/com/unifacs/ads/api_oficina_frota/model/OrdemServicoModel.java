package com.unifacs.ads.api_oficina_frota.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_ordem_servico")
public class OrdemServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "ordemServico")
    private List<EmprestimoModel> emprestimos;

    public OrdemServicoModel() {
    }

    public OrdemServicoModel(UUID id, String descricao, List<EmprestimoModel> emprestimos) {
        this.id = id;
        this.descricao = descricao;
        this.emprestimos = emprestimos;
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

    public List<EmprestimoModel> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<EmprestimoModel> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
