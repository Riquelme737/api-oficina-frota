package com.unifacs.ads.api_oficina_frota.model;

import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    public UsuarioModel() {
    }

    public UsuarioModel(UUID id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
