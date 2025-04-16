package com.paisapi.pais_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"login"})
        })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O login é obrigatório")
    @Column(length = 50, nullable = false, unique = true)
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    @Column(length = 255, nullable = false)
    private String senha;

    @NotBlank(message = "O nome é obrigatório")
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean administrador;

    public Usuario() {

    }

    public Usuario(String login, String senha, String nome, boolean administrador) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.administrador = administrador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
}