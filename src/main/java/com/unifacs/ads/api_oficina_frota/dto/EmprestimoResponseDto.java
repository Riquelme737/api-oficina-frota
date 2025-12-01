package com.unifacs.ads.api_oficina_frota.dto;

import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.enums.Turno;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;

import java.time.Instant;
import java.util.UUID;

public record EmprestimoResponseDto(
        String id,
        String emailOperador,
        String nomeFerramenta,
        String descricaoOs,
        Instant checkIn,
        Turno turno,
        StatusDevolucao status
) {
    public EmprestimoResponseDto (EmprestimoModel model){
        this(
                model.getId().toString(),
                model.getOperador().getEmail(),
                model.getFerramenta().getNome(),
                model.getOrdemServico().getDescricao(),
                model.getCheckIn(),
                model.getTurnoTrabalho(),
                model.getStatusDevolucao()
        );
    }
}
