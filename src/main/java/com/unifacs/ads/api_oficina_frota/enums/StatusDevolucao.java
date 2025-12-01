package com.unifacs.ads.api_oficina_frota.enums;

public enum StatusDevolucao {
    PENDENTE ("Pendente"),
    NORMAL("Normal"),
    ATRASADO("Atrasado"),
    DANIFICADO("Danificado"),
    ATRASADO_DANIFICADO("Atrasado e Danificado");

    private String nome;

    StatusDevolucao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
