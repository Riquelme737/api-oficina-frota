package com.unifacs.ads.api_oficina_frota.repository;

import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FerramentaRepository extends JpaRepository<FerramentaModel, UUID> {
}
