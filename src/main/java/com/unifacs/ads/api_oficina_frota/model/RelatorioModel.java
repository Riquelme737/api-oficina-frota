package com.unifacs.ads.api_oficina_frota.model;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioModel {

    private Instant dataGeracao;
    private List<EmprestimoModel> dados;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public RelatorioModel(List<EmprestimoModel> dados) {
        this.dados = dados;
        this.dataGeracao = Instant.now();
    }

    public String exportar() {
        StringBuilder csv = new StringBuilder();

        //csv.append("Relatório de Histórico Fechado;Gerado em: ;").append(FORMATTER.format(dataGeracao)).append("\n\n");

        csv.append("id_emprestimo;operador;ferramenta;ordem_servico;check_in;check_out;duracao_horas;turno;label\n");

        for (EmprestimoModel emp : dados) {
            csv.append(emp.getId()).append(";");
            csv.append(emp.getOperador().getEmail()).append(";");
            csv.append(emp.getFerramenta().getNome()).append(";");

            if (emp.getOrdemServico() != null) {
                csv.append(emp.getOrdemServico().getDescricao()).append(";");
            }

            csv.append(formatarData(emp.getCheckIn())).append(";");
            csv.append(formatarData(emp.getCheckOut())).append(";");

            if (emp.getCheckIn() != null && emp.getCheckOut() != null) {
                long horas = Duration.between(emp.getCheckIn(), emp.getCheckOut()).toHours();
                csv.append(horas).append(";");
            } else {
                csv.append("0;");
            }

            csv.append(emp.getTurnoTrabalho()).append(";");
            csv.append(emp.getStatusDevolucao()).append("\n");
        }

        return csv.toString();
    }

    private String formatarData(Instant data) {
        if (data == null) {
            return "---";
        }
        return FORMATTER.format(data);
    }

    public List<EmprestimoModel> getDados() {
        return dados;
    }

    public void setDados(List<EmprestimoModel> dados) {
        this.dados = dados;
    }

    public Instant getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Instant dataGeracao) {
        this.dataGeracao = dataGeracao;
    }
}
