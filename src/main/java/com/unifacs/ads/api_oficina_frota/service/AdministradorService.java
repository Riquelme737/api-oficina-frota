package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.dto.CreateOperadorDto;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.repository.AdministradorRepository;
import com.unifacs.ads.api_oficina_frota.repository.OperadorRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final OperadorRepository operadorRepository;

    public AdministradorService(AdministradorRepository administradorRepository, OperadorRepository operadorRepository) {
        this.administradorRepository = administradorRepository;
        this.operadorRepository = operadorRepository;
    }

    public UUID cadastrarOperador(CreateOperadorDto createOperadorDto) {
        OperadorModel operador = new OperadorModel(
                null,
                createOperadorDto.email(),
                createOperadorDto.senha()
        );

        OperadorModel operadorSalvo = operadorRepository.save(operador);
        return operadorSalvo.getId();
    }
}
