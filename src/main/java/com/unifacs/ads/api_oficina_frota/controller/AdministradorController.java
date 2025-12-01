package com.unifacs.ads.api_oficina_frota.controller;

import com.unifacs.ads.api_oficina_frota.dto.CreateFerramentaDto;
import com.unifacs.ads.api_oficina_frota.dto.CreateOperadorDto;
import com.unifacs.ads.api_oficina_frota.dto.FerramentaResponseDto;
import com.unifacs.ads.api_oficina_frota.dto.OperadorResponseDto;
import com.unifacs.ads.api_oficina_frota.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdministradorController {

    private final AdministradorService service;

    public AdministradorController(AdministradorService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar-operador")
    public ResponseEntity<OperadorResponseDto> cadastrarOperador (@RequestBody CreateOperadorDto createOperadorDto) {
        UUID operadorId = service.cadastrarOperador(createOperadorDto);
        return ResponseEntity.created(URI.create("/operador/id/" + operadorId.toString())).build();
    }

    @PostMapping("/cadastrar-ferramenta")
    public ResponseEntity<FerramentaResponseDto> cadastrarFerramenta(@RequestBody CreateFerramentaDto createFerramentaDto) {
        UUID ferramentaId = service.cadastrarFerramenta(createFerramentaDto);
        return ResponseEntity.created(URI.create("/ferramenta/id/" + ferramentaId.toString())).build();
    }
}
