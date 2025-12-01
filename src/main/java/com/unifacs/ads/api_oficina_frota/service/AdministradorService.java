package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.dto.CreateFerramentaDto;
import com.unifacs.ads.api_oficina_frota.dto.CreateOperadorDto;
import com.unifacs.ads.api_oficina_frota.dto.CreateOrdemServicoDto;
import com.unifacs.ads.api_oficina_frota.enums.StatusFerramenta;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.model.OrdemServicoModel;
import com.unifacs.ads.api_oficina_frota.repository.AdministradorRepository;
import com.unifacs.ads.api_oficina_frota.repository.FerramentaRepository;
import com.unifacs.ads.api_oficina_frota.repository.OperadorRepository;
import com.unifacs.ads.api_oficina_frota.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final OperadorRepository operadorRepository;
    private final FerramentaRepository ferramentaRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public AdministradorService(AdministradorRepository administradorRepository, OperadorRepository operadorRepository, FerramentaRepository ferramentaRepository, OrdemServicoRepository ordemServicoRepository) {
        this.administradorRepository = administradorRepository;
        this.operadorRepository = operadorRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.ordemServicoRepository = ordemServicoRepository;
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

    public UUID cadastrarFerramenta(CreateFerramentaDto createFerramentaDto){
        FerramentaModel ferramenta = new FerramentaModel(
                null,
                createFerramentaDto.nome(),
                StatusFerramenta.DISPONIVEL,
                null
        );

        FerramentaModel ferramentaSalva = ferramentaRepository.save(ferramenta);
        return ferramentaSalva.getId();
    }

    public UUID cadastrarOrdemServico(CreateOrdemServicoDto createOrdemServicoDto) {
        OrdemServicoModel ordemServico = new OrdemServicoModel(
                null,
                createOrdemServicoDto.descricao(),
                null
        );

        OrdemServicoModel osSalvo = ordemServicoRepository.save(ordemServico);
        return osSalvo.getId();
    }
}
