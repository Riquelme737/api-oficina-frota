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
import java.util.Random;

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
        Random random = new Random();

        // ----------------------------------------------------------------
        // 1. POPULAR BANCO COM VARIEDADE (10 de cada)
        // ----------------------------------------------------------------

        // --- Operadores ---
        List<String> nomesOps = Arrays.asList(
                "Joao Silva", "Maria Santos", "Rafael Costa", "Ana Pereira", "Carlos Oliveira",
                "Beatriz Souza", "Marcos Lima", "Fernanda Alves", "Lucas Rocha", "Juliana Mendes"
        );
        List<OperadorModel> operadores = new ArrayList<>();
        for (String nome : nomesOps) {
            String email = nome.toLowerCase().replace(" ", ".") + "@oficina.br";
            operadores.add(new OperadorModel(null, email, "123456"));
        }
        operadores = operadorRepository.saveAll(operadores);

        // --- Ferramentas ---
        List<String> nomesFerramentas = Arrays.asList(
                "Furadeira Bosh", "Torquímetro Digital", "Macaco Hidráulico", "Chave Dinamométrica",
                "Scanner Automotivo", "Lixadeira Orbital", "Parafusadeira Makita", "Multímetro Fluke",
                "Serra Circular", "Alicate de Pressão"
        );
        List<FerramentaModel> ferramentas = new ArrayList<>();
        for (String nomeFer : nomesFerramentas) {
            ferramentas.add(new FerramentaModel(null, nomeFer, StatusFerramenta.DISPONIVEL, null));
        }
        ferramentas = ferramentaRepository.saveAll(ferramentas);

        // --- Ordens de Serviço ---
        List<String> descricoesOS = Arrays.asList(
                "Troca de Motor Scania", "Revisão Freios Volvo", "Alinhamento e Balanceamento",
                "Troca de Óleo Geral", "Reparo Elétrico Painel", "Troca de Correia Dentada",
                "Retífica de Cabeçote", "Manutenção Suspensão", "Diagnóstico Injeção Eletrônica",
                "Troca de Embreagem Caminhão"
        );
        List<OrdemServicoModel> ordens = new ArrayList<>();
        for (String desc : descricoesOS) {
            ordens.add(new OrdemServicoModel(null, desc, null));
        }
        ordens = ordemServicoRepository.saveAll(ordens);


        // ----------------------------------------------------------------
        // 2. GERAR 300 EMPRÉSTIMOS DE HISTÓRICO (Com aleatoriedade realista)
        // ----------------------------------------------------------------
        List<EmprestimoModel> historico = new ArrayList<>();

        for (int i = 0; i < 300; i++) {

            // Sorteia aleatoriamente um Operador, uma Ferramenta e uma OS da lista
            OperadorModel op = operadores.get(random.nextInt(operadores.size()));
            FerramentaModel fer = ferramentas.get(random.nextInt(ferramentas.size()));
            OrdemServicoModel os = ordens.get(random.nextInt(ordens.size()));

            // Define status (50% de chance de ser atrasado para balancear o dataset)
            boolean isNormal = (i % 2 == 0);
            StatusDevolucao status = isNormal ? StatusDevolucao.NORMAL : StatusDevolucao.ATRASADO;

            // Define turno aleatório
            Turno[] turnos = Turno.values();
            Turno turno = turnos[random.nextInt(turnos.length)];

            // DATAS: Espalha os empréstimos nos últimos 100 dias
            int diasAtras = random.nextInt(100) + 1;
            Instant checkIn = Instant.now().minus(diasAtras, ChronoUnit.DAYS);

            // LÓGICA DE DURAÇÃO REALISTA
            Instant checkOut;
            if (isNormal) {
                // Devolveu entre 1h e 7h depois (dentro do turno)
                int horasUso = random.nextInt(7) + 1;
                checkOut = checkIn.plus(horasUso, ChronoUnit.HOURS);
            } else {
                // Atrasou! Devolveu entre 9h e 72h depois (1 a 3 dias com a ferramenta)
                int horasAtraso = random.nextInt(63) + 9;
                checkOut = checkIn.plus(horasAtraso, ChronoUnit.HOURS);
            }

            historico.add(new EmprestimoModel(
                    null, checkIn, checkOut, turno, status, op, fer, os
            ));
        }
        emprestimoRepository.saveAll(historico);

        System.out.println("------------------------------------------------");
        System.out.println("TEST DB: BANCO POPULADO COM SUCESSO!");
        System.out.println(" - 10 Operadores");
        System.out.println(" - 10 Ferramentas");
        System.out.println(" - 10 Tipos de O.S.");
        System.out.println(" - 300 Registros de Histórico gerados.");
        System.out.println("------------------------------------------------");
    }
}