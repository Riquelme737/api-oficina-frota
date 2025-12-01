package com.unifacs.ads.api_oficina_frota.dto;

import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;
import com.unifacs.ads.api_oficina_frota.enums.Turno;
import com.unifacs.ads.api_oficina_frota.model.EmprestimoModel;

import java.time.Instant;

public record EmprestimoCheckOutResponseDto(
        String id,
        String emailOperador,
        String nomeFerramenta,
        String descricaoOs,
        Instant checkIn,
        Instant checkOut,
        Turno turno,
        StatusDevolucao status
) {
    public EmprestimoCheckOutResponseDto(EmprestimoModel model){
        this(
                model.getId().toString(),
                model.getOperador().getEmail(),
                model.getFerramenta().getNome(),
                model.getOrdemServico().getDescricao(),
                model.getCheckIn(),
                model.getCheckOut(),
                model.getTurnoTrabalho(),
                model.getStatusDevolucao()
        );
    }
}
