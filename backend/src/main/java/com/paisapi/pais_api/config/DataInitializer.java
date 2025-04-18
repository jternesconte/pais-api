package com.paisapi.pais_api.config;

import com.paisapi.pais_api.model.Pais;
import com.paisapi.pais_api.model.Usuario;
import com.paisapi.pais_api.repository.PaisRepository;
import com.paisapi.pais_api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PaisRepository paisRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario usuario1 = new Usuario("convidado", passwordEncoder.encode("manager"), "Usuário convidado", false);
                Usuario usuario2 = new Usuario("admin", passwordEncoder.encode("suporte"), "Gestor", true);

                usuarioRepository.save(usuario1);
                usuarioRepository.save(usuario2);
            }

            if(paisRepository.count() == 0) {
                Pais pais1 = new Pais("Brasil" ,"BR", "Brasileiro");
                Pais pais2 = new Pais("Argentina" ,"AR", "Argentino");
                Pais pais3 = new Pais("Alemanha" ,"AL", "Alemão");

                paisRepository.save(pais1);
                paisRepository.save(pais2);
                paisRepository.save(pais3);
            }
        };
    }
}
