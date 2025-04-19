package com.paisapi.pais_api.controller;

import com.paisapi.pais_api.dto.UsuarioAutenticadoDTO;
import com.paisapi.pais_api.dto.UsuarioDTO;
import com.paisapi.pais_api.dto.UsuarioLoginDTO;
import com.paisapi.pais_api.model.Token;
import com.paisapi.pais_api.model.Usuario;
import com.paisapi.pais_api.repository.TokenRepository;
import com.paisapi.pais_api.repository.UsuarioRepository;
import com.paisapi.pais_api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioAutenticadoDTO autenticarUsuario(UsuarioLoginDTO usuarioDetalhes) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(usuarioDetalhes.getLogin(),usuarioDetalhes.getSenha());

            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
            Usuario usuario = (Usuario) auth.getPrincipal();

            return new UsuarioAutenticadoDTO(
                    usuario.getLogin(),
                    usuario.getNome(),
                    token,
                    usuario.isAdministrador(),
                    true
            );
        } catch (Exception e) {
            return new UsuarioAutenticadoDTO(
                    usuarioDetalhes.getLogin(),
                    null,
                    null,
                    false,
                    false
            );
        }
    }

    public ResponseEntity<String> renovarToken(String token) {
        Optional<Token> tokenExistente = tokenRepository.findByToken(token);

        if(tokenExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inexistente");
        }

        Token novoToken = tokenService.renovarValidadeToken(token);

        tokenExistente.get().setToken(novoToken.getToken());
        tokenExistente.get().setExpiracao(novoToken.getExpiracao());


        tokenRepository.save(tokenExistente.get());
        return ResponseEntity.ok(tokenExistente.get().getToken());
    }

    public UsuarioLoginDTO criarUsuario(UsuarioDTO novoUsuario) {
        if(usuarioRepository.findByLogin(novoUsuario.getLogin()) != null) {
            return null;
        }



        String encryptedPassword = passwordEncoder.encode(novoUsuario.getSenha());
        Usuario usuario = new Usuario(
                novoUsuario.getLogin(),
                encryptedPassword,
                novoUsuario.getNome(),
                novoUsuario.isAdministrador()
        );

        usuarioRepository.save(usuario);
        UsuarioLoginDTO usuarioLoginDTO = new UsuarioLoginDTO(
                usuario.getLogin(),
                usuario.getSenha()
        );

        return usuarioLoginDTO;

    }

    public boolean isAdministrador() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }
}
