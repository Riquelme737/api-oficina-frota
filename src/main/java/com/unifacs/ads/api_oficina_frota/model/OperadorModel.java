package com.unifacs.ads.api_oficina_frota.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_operador")
public class OperadorModel extends UsuarioModel{

    @OneToMany(mappedBy = "operador")
    private List<EmprestimoModel> emprestimos;

    public OperadorModel(UUID id, String email, String senha, List<EmprestimoModel> emprestimos) {
        super(id, email, senha);
        this.emprestimos = emprestimos;
    }
}
