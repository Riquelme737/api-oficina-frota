package com.unifacs.ads.api_oficina_frota.repository;

import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, UUID> {

    List<EmprestimoModel> findByStatusDevolucao(StatusDevolucao statusDevolucao);
    List<EmprestimoModel> findByFerramenta(FerramentaModel ferramenta);
    List<EmprestimoModel> findByOperador(OperadorModel operadorModel);
}
