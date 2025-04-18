package com.paisapi.pais_api.repository;

import com.paisapi.pais_api.model.Token;
import com.paisapi.pais_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByLogin(String login);
}
