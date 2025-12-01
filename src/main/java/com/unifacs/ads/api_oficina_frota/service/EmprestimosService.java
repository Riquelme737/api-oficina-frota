package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.dto.CreateEmprestimoDto;
import com.unifacs.ads.api_oficina_frota.dto.EmprestimoResponseDto;
import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.enums.StatusFerramenta;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.model.OrdemServicoModel;
import com.unifacs.ads.api_oficina_frota.repository.EmprestimoRepository;
import com.unifacs.ads.api_oficina_frota.repository.FerramentaRepository;
import com.unifacs.ads.api_oficina_frota.repository.OperadorRepository;
import com.unifacs.ads.api_oficina_frota.repository.OrdemServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.UUID;

@Service
public class EmprestimosService {

    private final EmprestimoRepository emprestimoRepository;
    private final FerramentaRepository ferramentaRepository;
    private final OperadorRepository operadorRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public EmprestimosService(EmprestimoRepository emprestimoRepository, FerramentaRepository ferramentaRepository, OperadorRepository operadorRepository, OrdemServicoRepository ordemServicoRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.operadorRepository = operadorRepository;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    @Transactional
    public EmprestimoResponseDto criarEmprestimo(@RequestBody CreateEmprestimoDto createEmprestimoDto) {
        OperadorModel operador = operadorRepository.findById(UUID.fromString(createEmprestimoDto.idOperador()))
                .orElseThrow(() -> new RuntimeException("Operador não encontrado"));

        FerramentaModel ferramenta = ferramentaRepository.findById(UUID.fromString(createEmprestimoDto.idFerramenta()))
                .orElseThrow(() -> new RuntimeException("Ferramenta não encontrada"));

        if (ferramenta.getStatus() == StatusFerramenta.EM_USO) {
            throw new RuntimeException("Ferramenta já está em uso!");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(UUID.fromString(createEmprestimoDto.idOs()))
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrado"));

        EmprestimoModel emprestimoModel = new EmprestimoModel(
                null,
                Instant.now(),
                null,
                createEmprestimoDto.turno(),
                StatusDevolucao.PENDENTE,
                operador,
                ferramenta,
                ordemServico
        );

        ferramenta.setStatus(StatusFerramenta.EM_USO);
        ferramentaRepository.save(ferramenta);

        EmprestimoModel emprestimoSalvo = emprestimoRepository.save(emprestimoModel);

        return new EmprestimoResponseDto(emprestimoSalvo);
    }
}
