package com.unifacs.ads.api_oficina_frota.repository;

import com.unifacs.ads.api_oficina_frota.model.AdministradorModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdministradorRepository extends JpaRepository<AdministradorModel, UUID> {
}
