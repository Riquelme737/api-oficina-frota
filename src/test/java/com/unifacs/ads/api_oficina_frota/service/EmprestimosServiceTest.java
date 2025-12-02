package com.unifacs.ads.api_oficina_frota.service;

import com.unifacs.ads.api_oficina_frota.dto.CreateDevolucaoDto;
import com.unifacs.ads.api_oficina_frota.dto.CreateEmprestimoDto;
import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckInResponseDto;
import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckOutResponseDto;
import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.enums.StatusFerramenta;
import com.unifacs.ads.api_oficina_frota.enums.Turno;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;
import com.unifacs.ads.api_oficina_frota.model.FerramentaModel;
import com.unifacs.ads.api_oficina_frota.model.OperadorModel;
import com.unifacs.ads.api_oficina_frota.model.OrdemServicoModel;
import com.unifacs.ads.api_oficina_frota.repository.EmprestimoRepository;
import com.unifacs.ads.api_oficina_frota.repository.FerramentaRepository;
import com.unifacs.ads.api_oficina_frota.repository.OperadorRepository;
import com.unifacs.ads.api_oficina_frota.repository.OrdemServicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmprestimosServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;
    @Mock
    private FerramentaRepository ferramentaRepository;
    @Mock
    private OperadorRepository operadorRepository;
    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @InjectMocks
    private EmprestimosService emprestimosService;

    @Test
    @DisplayName("Deve criar empréstimo com sucesso quando tudo estiver OK")
    void criarEmprestimoSucesso() {
        // ARRANGE (Cenário)
        UUID opId = UUID.randomUUID();
        UUID ferId = UUID.randomUUID();
        UUID osId = UUID.randomUUID();
        CreateEmprestimoDto dto = new CreateEmprestimoDto(opId.toString(), ferId.toString(), osId.toString(), Turno.MANHA);

        OperadorModel operador = new OperadorModel(opId, "op@test.com", "123");
        FerramentaModel ferramenta = new FerramentaModel(ferId, "Chave", StatusFerramenta.DISPONIVEL, null);
        OrdemServicoModel os = new OrdemServicoModel(osId, "Revisão", null);

        // Mocks do comportamento do banco
        when(operadorRepository.findById(opId)).thenReturn(Optional.of(operador));
        when(ferramentaRepository.findById(ferId)).thenReturn(Optional.of(ferramenta));
        when(ordemServicoRepository.findById(osId)).thenReturn(Optional.of(os));
        // Simula o salvamento retornando um objeto com ID gerado
        when(emprestimoRepository.save(any(EmprestimoModel.class))).thenAnswer(i -> {
            EmprestimoModel e = i.getArgument(0);
            e.setId(UUID.randomUUID());
            return e;
        });

        // ACT (Ação)
        EmprestimoCheckInResponseDto resultado = emprestimosService.criarEmprestimo(dto);

        // ASSERT (Verificação)
        assertNotNull(resultado);
        assertEquals("Chave", resultado.nomeFerramenta());
        assertEquals(StatusDevolucao.PENDENTE, resultado.status());

        // Verifica se a ferramenta mudou para EM_USO
        assertEquals(StatusFerramenta.EM_USO, ferramenta.getStatus());
        verify(ferramentaRepository, times(1)).save(ferramenta);
    }

    @Test
    @DisplayName("Não deve permitir empréstimo de ferramenta EM_USO")
    void criarEmprestimoFalhaFerramentaEmUso() {
        // ARRANGE
        UUID ferId = UUID.randomUUID();
        CreateEmprestimoDto dto = new CreateEmprestimoDto(UUID.randomUUID().toString(), ferId.toString(), UUID.randomUUID().toString(), Turno.MANHA);

        // Simula operador existente, mas ferramenta ocupada
        when(operadorRepository.findById(any())).thenReturn(Optional.of(new OperadorModel()));
        FerramentaModel ferramentaOcupada = new FerramentaModel(ferId, "Chave", StatusFerramenta.EM_USO, null);
        when(ferramentaRepository.findById(ferId)).thenReturn(Optional.of(ferramentaOcupada));

        // ACT & ASSERT
        Exception exception = assertThrows(RuntimeException.class, () -> {
            emprestimosService.criarEmprestimo(dto);
        });

        assertEquals("Ferramenta já está em uso!", exception.getMessage());
        // Garante que NUNCA salvou nada no banco
        verify(emprestimoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve realizar devolução NORMAL (dentro do prazo de 8h)")
    void realizarDevolucaoNormal() {
        // ARRANGE
        UUID empId = UUID.randomUUID();
        CreateDevolucaoDto dto = new CreateDevolucaoDto(empId.toString(), null); // Status no DTO é ignorado na sua lógica nova, pois é calculado

        FerramentaModel ferramenta = new FerramentaModel(UUID.randomUUID(), "Chave", StatusFerramenta.EM_USO, null);
        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setId(empId);
        emprestimo.setFerramenta(ferramenta);
        emprestimo.setCheckIn(Instant.now().minus(4, ChronoUnit.HOURS)); // Pegou há 4h (dentro do prazo)
        emprestimo.setOrdemServico(new OrdemServicoModel(UUID.randomUUID(), "OS", null));
        emprestimo.setOperador(new OperadorModel(UUID.randomUUID(), "a", "b"));
        emprestimo.setTurnoTrabalho(Turno.MANHA);

        when(emprestimoRepository.findById(empId)).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(EmprestimoModel.class))).thenAnswer(i -> i.getArgument(0));

        // ACT
        EmprestimoCheckOutResponseDto resultado = emprestimosService.realizarDevolucao(dto);

        // ASSERT
        assertEquals(StatusDevolucao.NORMAL, resultado.status()); // Esperado NORMAL
        assertNotNull(resultado.checkOut());
        assertEquals(StatusFerramenta.DISPONIVEL, ferramenta.getStatus()); // Ferramenta liberada
    }

    @Test
    @DisplayName("Deve realizar devolução como ATRASADO (fora do prazo de 8h)")
    void realizarDevolucaoAtrasado() {
        // ARRANGE
        UUID empId = UUID.randomUUID();
        CreateDevolucaoDto dto = new CreateDevolucaoDto(empId.toString(), null);

        FerramentaModel ferramenta = new FerramentaModel(UUID.randomUUID(), "Chave", StatusFerramenta.EM_USO, null);
        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setId(empId);
        emprestimo.setFerramenta(ferramenta);
        // Pegou há 10 horas (limite é 8)
        emprestimo.setCheckIn(Instant.now().minus(10, ChronoUnit.HOURS));
        emprestimo.setOrdemServico(new OrdemServicoModel(UUID.randomUUID(), "OS", null));
        emprestimo.setOperador(new OperadorModel(UUID.randomUUID(), "a", "b"));
        emprestimo.setTurnoTrabalho(Turno.MANHA);

        when(emprestimoRepository.findById(empId)).thenReturn(Optional.of(emprestimo));
        when(emprestimoRepository.save(any(EmprestimoModel.class))).thenAnswer(i -> i.getArgument(0));

        // ACT
        EmprestimoCheckOutResponseDto resultado = emprestimosService.realizarDevolucao(dto);

        // ASSERT
        assertEquals(StatusDevolucao.ATRASADO, resultado.status()); // Esperado ATRASADO
    }
}