package com.unifacs.ads.api_oficina_frota.enums;

public enum StatusFerramenta {

    DISPONIVEL ("Disponível"),
    EM_USO ("Em uso");

    private String nome;

    StatusFerramenta(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
