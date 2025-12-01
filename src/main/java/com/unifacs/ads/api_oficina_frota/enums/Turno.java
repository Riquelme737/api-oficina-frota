package com.unifacs.ads.api_oficina_frota.enums;

public enum Turno {
    MANHA ("Manhã"),
    TARDE ("Tarde"),
    NOITE ("Noite");

    private String nome;

    Turno(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
