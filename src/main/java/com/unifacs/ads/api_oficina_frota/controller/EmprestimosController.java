package com.unifacs.ads.api_oficina_frota.controller;

import com.unifacs.ads.api_oficina_frota.dto.CreateEmprestimoDto;
import com.unifacs.ads.api_oficina_frota.dto.EmprestimoResponseDto;
import com.unifacs.ads.api_oficina_frota.service.EmprestimosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimosController {

    private final EmprestimosService service;

    public EmprestimosController(EmprestimosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDto> criarEmprestimo(@RequestBody CreateEmprestimoDto createEmprestimoDto) {
        return ResponseEntity.ok(service.criarEmprestimo(createEmprestimoDto));
    }
}
