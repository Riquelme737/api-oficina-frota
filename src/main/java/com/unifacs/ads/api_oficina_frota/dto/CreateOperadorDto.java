package com.unifacs.ads.api_oficina_frota.dto;

import com.unifacs.ads.api_oficina_frota.model.OperadorModel;

public record CreateOperadorDto(
        String email,
        String senha
) {
}
