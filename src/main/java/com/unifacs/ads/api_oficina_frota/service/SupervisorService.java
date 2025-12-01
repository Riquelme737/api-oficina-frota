package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckOutResponseDto;
import com.unifacs.ads.api_oficina_frota.dto.OperadorResponseDto;
import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.model.RelatorioModel;
import com.unifacs.ads.api_oficina_frota.repository.EmprestimoRepository;
import com.unifacs.ads.api_oficina_frota.repository.FerramentaRepository;
import com.unifacs.ads.api_oficina_frota.repository.OperadorRepository;
import com.unifacs.ads.api_oficina_frota.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final FerramentaRepository ferramentaRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final OperadorRepository operadorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository, FerramentaRepository ferramentaRepository, EmprestimoRepository emprestimoRepository, OperadorRepository operadorRepository) {
        this.supervisorRepository = supervisorRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.operadorRepository = operadorRepository;
    }

    public List<EmprestimoCheckOutResponseDto> consultarHistoricoFerramenta(String idFerramenta) {
        FerramentaModel ferramenta = ferramentaRepository.findById(UUID.fromString(idFerramenta))
                .orElseThrow(() -> new RuntimeException("Ferramenta não encontrada"));

        return emprestimoRepository.findByFerramenta(ferramenta).stream()
                .map(EmprestimoCheckOutResponseDto::new)
                .toList();
    }

    public List<EmprestimoCheckOutResponseDto> consultarHistoricoOperador(String idOperador) {
        OperadorModel operadorModel = operadorRepository.findById(UUID.fromString(idOperador))
                .orElseThrow(() -> new RuntimeException("Operador não encontrado"));

        return emprestimoRepository.findByOperador(operadorModel).stream()
                .map(EmprestimoCheckOutResponseDto::new)
                .toList();
    }

    public List<EmprestimoCheckOutResponseDto> todosOsEmprestimosExcluindoPendente() {
        return emprestimoRepository.findByStatusDevolucaoNot(StatusDevolucao.PENDENTE)
                .stream().map(EmprestimoCheckOutResponseDto::new)
                .toList();
    }

    public String gerarRelatorio() {
        List<EmprestimoModel> listaHistorico = emprestimoRepository.findByStatusDevolucaoNot(StatusDevolucao.PENDENTE);

        RelatorioModel relatorio = new RelatorioModel(listaHistorico);

        return relatorio.exportar();
    }
}
