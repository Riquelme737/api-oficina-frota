package com.unifacs.ads.api_oficina_frota.config;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private FerramentaRepository ferramentaRepository;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. CRIAR OPERADORES
        OperadorModel p1 = new OperadorModel(null, "joao.silva@unifacs.br", "123456", null);
        OperadorModel p2 = new OperadorModel(null, "maria.santos@unifacs.br", "abcdef", null);
        OperadorModel p3 = new OperadorModel(null, "rafael.silva@unifacs.br", "1ds53", null);
        operadorRepository.saveAll(Arrays.asList(p1, p2, p3));

        // 2. CRIAR FERRAMENTAS
        // Ferramentas DISPONIVEIS (já foram devolvidas ou nunca usadas)
        FerramentaModel f1 = new FerramentaModel(null, "Furadeira Bosh", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f3 = new FerramentaModel(null, "Macaco Hidráulico", StatusFerramenta.DISPONIVEL, null);

        // Ferramentas EM_USO (estão atualmente emprestadas)
        FerramentaModel f2 = new FerramentaModel(null, "Torquímetro Digital", StatusFerramenta.EM_USO, null);
        FerramentaModel f4 = new FerramentaModel(null, "Chave Dinalométrica", StatusFerramenta.EM_USO, null);

        ferramentaRepository.saveAll(Arrays.asList(f1, f2, f3, f4));

        // 3. CRIAR ORDENS DE SERVIÇO
        OrdemServicoModel os1 = new OrdemServicoModel(null, "Troca de Motor Scania", null);
        OrdemServicoModel os2 = new OrdemServicoModel(null, "Revisão Freios Volvo", null);
        ordemServicoRepository.saveAll(Arrays.asList(os1, os2));

        // --- POPULANDO EMPRÉSTIMOS COM NOVOS STATUS ---

        // CENÁRIO 1: Empréstimo ATIVO (PENDENTE)
        // Pegou há 2 horas e ainda não devolveu.
        EmprestimoModel e1 = new EmprestimoModel(
                null,
                Instant.now().minus(2, ChronoUnit.HOURS),
                null, // Sem data de devolução
                Turno.MANHA,
                StatusDevolucao.PENDENTE,
                p1,
                f2, // Torquímetro (EM_USO)
                os1
        );

        // CENÁRIO 2: Outro Empréstimo ATIVO (PENDENTE)
        // Pegou há 30 minutos.
        EmprestimoModel e2 = new EmprestimoModel(
                null,
                Instant.now().minus(30, ChronoUnit.MINUTES),
                null, // Sem data de devolução
                Turno.TARDE,
                StatusDevolucao.PENDENTE,
                p2,
                f4, // Chave Dinalométrica (EM_USO)
                os2
        );

        // CENÁRIO 3: Devolução no Prazo (NORMAL)
        // Pegou há 5 dias, devolveu 4 horas depois (rápido).
        Instant dataCheckInNormal = Instant.now().minus(5, ChronoUnit.DAYS);
        EmprestimoModel e3 = new EmprestimoModel(
                null,
                dataCheckInNormal,
                dataCheckInNormal.plus(4, ChronoUnit.HOURS), // Devolveu 4h depois
                Turno.NOITE,
                StatusDevolucao.NORMAL,
                p3,
                f1, // Furadeira (DISPONIVEL)
                os1
        );

        // CENÁRIO 4: Devolução com Atraso (ATRASADO)
        // Pegou há 10 dias, mas só devolveu 20 horas depois (considerando turno de 8h, atrasou).
        Instant dataCheckInAtrasado = Instant.now().minus(10, ChronoUnit.DAYS);
        EmprestimoModel e4 = new EmprestimoModel(
                null,
                dataCheckInAtrasado,
                dataCheckInAtrasado.plus(20, ChronoUnit.HOURS), // Devolveu quase 1 dia depois
                Turno.MANHA,
                StatusDevolucao.ATRASADO,
                p1,
                f3, // Macaco (DISPONIVEL)
                os2
        );

        emprestimoRepository.saveAll(Arrays.asList(e1, e2, e3, e4));

        System.out.println("------------------------------------------------");
        System.out.println("TESTE DB: Carga realizada com status PENDENTE, NORMAL e ATRASADO.");
        System.out.println("------------------------------------------------");
    }
}