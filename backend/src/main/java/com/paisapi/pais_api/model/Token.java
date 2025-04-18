package com.paisapi.pais_api.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "token",
        uniqueConstraints = {})
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String token;

    @Column(length = 100, nullable = false)
    private String login;

    @Column(nullable = false)
    private Timestamp expiracao;

    @Column(nullable = false)
    private Boolean administrador;

    public Token() {

    }

    public Token(String token, String login, Timestamp expiracao, Boolean administrador) {
        this.token = token;
        this.login = login;
        this.expiracao = expiracao;
        this.administrador = administrador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Timestamp getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(Timestamp expiracao) {
        this.expiracao = expiracao;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
}
