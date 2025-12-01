package com.unifacs.ads.api_oficina_frota.repository;

import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.model.OrdemServicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoModel, UUID> {
}
