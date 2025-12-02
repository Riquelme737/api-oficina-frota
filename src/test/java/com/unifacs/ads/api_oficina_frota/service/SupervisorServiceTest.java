package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.enums.Turno;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.model.OrdemServicoModel;
import com.unifacs.ads.api_oficina_frota.repository.EmprestimoRepository;
import com.unifacs.ads.api_oficina_frota.repository.FerramentaRepository;
import com.unifacs.ads.api_oficina_frota.repository.OperadorRepository;
import com.unifacs.ads.api_oficina_frota.repository.SupervisorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupervisorServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;
    @Mock
    private SupervisorRepository supervisorRepository;
    @Mock
    private FerramentaRepository ferramentaRepository;
    @Mock
    private OperadorRepository operadorRepository;

    @InjectMocks
    private SupervisorService supervisorService;

    @Test
    @DisplayName("Deve gerar CSV corretamente ignorando pendentes")
    void gerarRelatorioCsv() {
        // ARRANGE
        // Cria um empréstimo fake já finalizado
        EmprestimoModel emp = new EmprestimoModel();
        emp.setId(UUID.randomUUID());
        emp.setOperador(new OperadorModel(UUID.randomUUID(), "teste@teste.com", "123"));
        emp.setFerramenta(new FerramentaModel(UUID.randomUUID(), "Martelo", null, null));
        emp.setOrdemServico(new OrdemServicoModel(UUID.randomUUID(), "OS-123", null));
        emp.setCheckIn(Instant.now());
        emp.setCheckOut(Instant.now().plusSeconds(3600));
        emp.setTurnoTrabalho(Turno.TARDE);
        emp.setStatusDevolucao(StatusDevolucao.NORMAL);

        // Quando o service chamar o repository buscando "NOT PENDENTE", retorna nossa lista
        when(emprestimoRepository.findByStatusDevolucaoNot(StatusDevolucao.PENDENTE))
                .thenReturn(List.of(emp));

        // ACT
        String csv = supervisorService.gerarRelatorio();

        // ASSERT
        // Verifica se o cabeçalho existe
        assertTrue(csv.contains("id_emprestimo;operador;ferramenta;ordem_servico"));
        // Verifica se os dados do mock estão lá
        assertTrue(csv.contains("teste@teste.com"));
        assertTrue(csv.contains("Martelo"));
        assertTrue(csv.contains("OS-123"));
        assertTrue(csv.contains("NORMAL"));
    }

    @Test
    @DisplayName("Deve gerar CSV vazio (apenas cabeçalho) se não houver histórico")
    void gerarRelatorioVazio() {
        // ARRANGE
        when(emprestimoRepository.findByStatusDevolucaoNot(StatusDevolucao.PENDENTE))
                .thenReturn(Collections.emptyList());

        // ACT
        String csv = supervisorService.gerarRelatorio();

        // ASSERT
        assertTrue(csv.contains("id_emprestimo;operador;ferramenta"));
        // Não deve ter dados, mas o cabeçalho deve estar lá
        int linhas = csv.split("\n").length;
        // Dependendo da sua implementação exata de RelatorioModel, pode ter 1 ou 2 linhas (headers)
        assertTrue(linhas <= 2);
    }
}