package com.unifacs.ads.api_oficina_frota.controller;

import com.unifacs.ads.api_oficina_frota.dto.CreateDevolucaoDto;
import com.unifacs.ads.api_oficina_frota.dto.CreateEmprestimoDto;
import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckInResponseDto;
import com.unifacs.ads.api_oficina_frota.dto.EmprestimoCheckOutResponseDto;
import com.unifacs.ads.api_oficina_frota.service.EmprestimosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimosController {

    private final EmprestimosService service;

    public EmprestimosController(EmprestimosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EmprestimoCheckInResponseDto> criarEmprestimo(@RequestBody CreateEmprestimoDto createEmprestimoDto) {
        return ResponseEntity.ok(service.criarEmprestimo(createEmprestimoDto));
    }

    @PutMapping("/devolucao")
    public ResponseEntity<EmprestimoCheckOutResponseDto> realizarDevolucao(@RequestBody CreateDevolucaoDto createDevolucaoDto) {
        return ResponseEntity.ok(service.realizarDevolucao(createDevolucaoDto));
    }
}
