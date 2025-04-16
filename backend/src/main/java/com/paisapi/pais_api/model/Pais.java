package com.paisapi.pais_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pais",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nome"}),
                @UniqueConstraint(columnNames = {"sigla"}),
                @UniqueConstraint(columnNames = {"gentilico"})
        })
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 2, nullable = false)
    private String sigla;

    @Column(length = 100, nullable = false)
    private String gentilico;

    public Pais() {

    }

    public Pais(String nome, String sigla, String gentilico) {
        this.nome = nome;
        this.sigla = sigla;
        this.gentilico = gentilico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getGentilico() {
        return gentilico;
    }

    public void setGentilico(String gentilico) {
        this.gentilico = gentilico;
    }
}
