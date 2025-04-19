package com.paisapi.pais_api.repository;

import com.paisapi.pais_api.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais, Long> {

    List<Pais> findByNomeContaining(String nome);
}
