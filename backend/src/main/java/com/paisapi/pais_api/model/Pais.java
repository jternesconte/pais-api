package com.paisapi.pais_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "pais",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nome"}),
                @UniqueConstraint(columnNames = {"sigla"}),
                @UniqueConstraint(columnNames = {"gentilico"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
