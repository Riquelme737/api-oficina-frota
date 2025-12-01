package com.unifacs.ads.api_oficina_frota.controller;

import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckOutResponseDto;
import com.unifacs.ads.api_oficina_frota.service.SupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    private final SupervisorService service;

    public SupervisorController(SupervisorService service) {
        this.service = service;
    }

    @GetMapping("/historico-ferramenta/{id}")
    public ResponseEntity<List<EmprestimoCheckOutResponseDto>> getHistoricoFerramenta(@PathVariable String id) {
        return ResponseEntity.ok(service.consultarHistoricoFerramenta(id));
    }

    @GetMapping("/historico-operador/{id}")
    public ResponseEntity<List<EmprestimoCheckOutResponseDto>> getHistoricoOperador(@PathVariable String id) {
        return ResponseEntity.ok(service.consultarHistoricoOperador(id));
    }


}
