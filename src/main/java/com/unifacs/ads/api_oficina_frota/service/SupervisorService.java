package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckOutResponseDto;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.repository.EmprestimoRepository;
import com.unifacs.ads.api_oficina_frota.repository.FerramentaRepository;
import com.unifacs.ads.api_oficina_frota.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final FerramentaRepository ferramentaRepository;
    private final EmprestimoRepository emprestimoRepository;

    public SupervisorService(SupervisorRepository supervisorRepository, FerramentaRepository ferramentaRepository, EmprestimoRepository emprestimoRepository) {
        this.supervisorRepository = supervisorRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<EmprestimoCheckOutResponseDto> consultarHistoricoFerramenta(String idFerramenta) {
        FerramentaModel ferramenta = ferramentaRepository.findById(UUID.fromString(idFerramenta))
                .orElseThrow(() -> new RuntimeException("Ferramenta não encontrada"));

        return emprestimoRepository.findByFerramenta(ferramenta).stream()
                .map(EmprestimoCheckOutResponseDto::new)
                .toList();
    }
}
