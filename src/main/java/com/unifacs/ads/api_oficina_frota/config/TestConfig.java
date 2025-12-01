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
        OperadorModel p1 = new OperadorModel(null, "joao.silva@unifacs.br", "123456", null);
        OperadorModel p2 = new OperadorModel(null, "maria.santos@unifacs.br", "abcdef", null);
        OperadorModel p3 = new OperadorModel(null, "rafael.silva@unifacs.br", "1ds53", null);
        OperadorModel p4 = new OperadorModel(null, "caio.feijao@unifacs.br", "zxcv", null);
        operadorRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

        FerramentaModel f1 = new FerramentaModel(null, "Furadeira Bosh", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f2 = new FerramentaModel(null, "Torquímetro Digital", StatusFerramenta.EM_USO, null);
        FerramentaModel f3 = new FerramentaModel(null, "Macaco Hidráulico", StatusFerramenta.DISPONIVEL, null);
        FerramentaModel f4 = new FerramentaModel(null, "Chave Dinalométrica", StatusFerramenta.EM_USO, null);
        ferramentaRepository.saveAll(Arrays.asList(f1, f2, f3, f4));

        OrdemServicoModel os1 = new OrdemServicoModel(null, "Troca de Motor Scania", null);
        ordemServicoRepository.save(os1);

        EmprestimoModel e1 = new EmprestimoModel(
                null,
                Instant.now(),
                null,
                Turno.MANHA,
                StatusDevolucao.ATRASADO,
                p1,
                f2,
                os1
        );
        emprestimoRepository.save(e1);

        System.out.println("------------------------------------------------");
        System.out.println("BANCO DE DADOS POPULADO COM SUCESSO!");
        System.out.println("Empréstimo ID: " + e1.getId());
        System.out.println("Ferramenta vinculada: " + e1.getFerramenta().getNome());
        System.out.println("------------------------------------------------");
    }
}
