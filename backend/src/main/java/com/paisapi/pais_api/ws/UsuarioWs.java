package com.paisapi.pais_api.ws;

import com.paisapi.pais_api.controller.UsuarioController;
import com.paisapi.pais_api.dto.UsuarioAutenticadoDTO;
import com.paisapi.pais_api.dto.UsuarioDTO;
import com.paisapi.pais_api.dto.UsuarioLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioWs {

    @Autowired
    private UsuarioController usuarioController;

    @Operation(summary = "Autentica um usuário e gera token JWT")
    @PostMapping("/autenticar")
    public @ResponseBody UsuarioAutenticadoDTO autenticarUsuario(@RequestBody UsuarioLoginDTO usuarioDetalhes) {
        return usuarioController.autenticarUsuario(usuarioDetalhes);
    }

    @GetMapping("/renovar-ticket")
    public ResponseEntity<String> renovarTicket(@RequestParam String token) {
        return usuarioController.renovarToken(token);
    }

    @Operation(summary = "Cadastra um usuário")
    @PostMapping("/cadastrar")
    public @ResponseBody UsuarioLoginDTO cadastrarUsuario(@RequestBody UsuarioDTO novoUsuario) {
        return usuarioController.criarUsuario(novoUsuario);
    }


}
