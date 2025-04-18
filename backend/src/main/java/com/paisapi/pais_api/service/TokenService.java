package com.paisapi.pais_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.paisapi.pais_api.model.Token;
import com.paisapi.pais_api.model.Usuario;
import com.paisapi.pais_api.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant dataExpiracao = criaDataExpiracao();
            String token = JWT.create()
                    .withIssuer("pais-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao)
                    .sign(algorithm);

            Optional<Token> tokenExistente = tokenRepository.findByLogin(usuario.getLogin());

            if(tokenExistente.isEmpty()) {
                Token novoToken = new Token(
                        token,
                        usuario.getLogin(),
                        Timestamp.from(dataExpiracao),
                        usuario.isAdministrador()
                );

                tokenRepository.save(novoToken);
            } else {
                tokenExistente.get().setToken(token);
                tokenExistente.get().setExpiracao(Timestamp.from(dataExpiracao));

                tokenRepository.save(tokenExistente.get());
            }



            return token;
        } catch(JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("pais-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch(JWTVerificationException e) {
            throw new RuntimeException("");
        }
    }

    private Instant criaDataExpiracao() {
        return LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
