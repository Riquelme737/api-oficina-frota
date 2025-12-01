package com.unifacs.ads.api_oficina_frota.dto;

import com.unifacs.ads.api_oficina_frota.enums.StatusDevolucao;

public record CreateDevolucaoDto(
        String idEmprestimo,
        StatusDevolucao statusDevolucao
) {
}
