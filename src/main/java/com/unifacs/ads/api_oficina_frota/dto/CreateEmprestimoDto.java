package com.unifacs.ads.api_oficina_frota.dto;

import com.unifacs.ads.api_oficina_frota.enums.Turno;

public record CreateEmprestimoDto(
        String idOperador,
        String idFerramenta,
        String idOs,
        Turno turno
) {
}
