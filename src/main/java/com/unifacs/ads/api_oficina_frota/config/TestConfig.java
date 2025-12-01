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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        // ----------------------------------------------------------------
        // 1. BASES (Operadores, Ferramentas, OS)
        // ----------------------------------------------------------------
        OperadorModel p1 = new OperadorModel(null, "joao.silva@unifacs.br", "123", null);
        OperadorModel p2 = new OperadorModel(null, "maria.santos@unifacs.br", "123", null);
        OperadorModel p3 = new OperadorModel(null, "rafael.silva@unifacs.br", "123", null);
        List<OperadorModel> operadores =  operadorRepository.saveAll(Arrays.asList(p1, p2, p3));

        FerramentaModel f1 = new FerramentaModel(null, "Furadeira Bosh", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f2 = new FerramentaModel(null, "Torquímetro Digital", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f3 = new FerramentaModel(null, "Macaco Hidráulico", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f4 = new FerramentaModel(null, "Chave Dinalométrica", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f5 = new FerramentaModel(null, "Scanner Automotivo", StatusFerramenta.DISPONIVEL, null);
        List<FerramentaModel> ferramentas = ferramentaRepository.saveAll(Arrays.asList(f1, f2, f3, f4, f5));

        OrdemServicoModel os1 = new OrdemServicoModel(null, "Troca de Motor Scania", null);
        OrdemServicoModel os2 = new OrdemServicoModel(null, "Revisão Freios Volvo", null);
        List<OrdemServicoModel> ordens = ordemServicoRepository.saveAll(Arrays.asList(os1, os2));

        // ----------------------------------------------------------------
        // 2. GERADOR AUTOMÁTICO DE 20 EMPRÉSTIMOS (Histórico)
        // ----------------------------------------------------------------
        List<EmprestimoModel> emprestimosParaSalvar = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {

            // Lógica para variar os dados usando o operador de resto (%)
            OperadorModel opAtual = operadores.get(i % operadores.size());
            FerramentaModel ferAtual = ferramentas.get(i % ferramentas.size());
            OrdemServicoModel osAtual = ordens.get(i % ordens.size());

            // Define se é NORMAL (Pares) ou ATRASADO (Ímpares)
            boolean isNormal = (i % 2 == 0);
            StatusDevolucao status = isNormal ? StatusDevolucao.NORMAL : StatusDevolucao.ATRASADO;

            // Define datas retroativas (Para não ficar tudo no mesmo dia)
            // Cada iteração volta 'i' dias no passado
            Instant dataCheckIn = Instant.now().minus(i, ChronoUnit.DAYS);
            Instant dataCheckOut;

            if (isNormal) {
                // Devolveu 4 horas depois
                dataCheckOut = dataCheckIn.plus(4, ChronoUnit.HOURS);
            } else {
                // Devolveu 26 horas depois (Atrasou o turno)
                dataCheckOut = dataCheckIn.plus(26, ChronoUnit.HOURS);
            }

            // Alterna turnos
            Turno turno = (i % 3 == 0) ? Turno.NOITE : (i % 2 == 0) ? Turno.TARDE : Turno.MANHA;

            EmprestimoModel emp = new EmprestimoModel(
                    null,
                    dataCheckIn,
                    dataCheckOut,
                    turno,
                    status,
                    opAtual,
                    ferAtual,
                    osAtual
            );
            emprestimosParaSalvar.add(emp);
        }

        emprestimoRepository.saveAll(emprestimosParaSalvar);

        // ----------------------------------------------------------------
        // 3. (OPCIONAL) CRIAR UM PENDENTE SÓ PARA TESTE DE EXCLUSÃO
        // ----------------------------------------------------------------
        FerramentaModel f6 = new FerramentaModel(null, "Solda MIG", StatusFerramenta.EM_USO, null);
        ferramentaRepository.save(f6);
        EmprestimoModel pendente = new EmprestimoModel(
                null, Instant.now(), null, Turno.MANHA, StatusDevolucao.PENDENTE,
                p1, f6, os1
        );
        emprestimoRepository.save(pendente);


        System.out.println("------------------------------------------------");
        System.out.println("CARGA DE DADOS MASSIVA EXECUTADA!");
        System.out.println("20 Empréstimos de Histórico gerados.");
        System.out.println("1 Empréstimo Pendente gerado.");
        System.out.println("Total no DB: " + emprestimoRepository.count());
        System.out.println("------------------------------------------------");
    }
}