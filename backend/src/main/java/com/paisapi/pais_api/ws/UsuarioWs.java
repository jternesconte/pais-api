package com.paisapi.pais_api.ws;

import com.paisapi.pais_api.controller.UsuarioController;
import com.paisapi.pais_api.dto.UsuarioAutenticadoDTO;
import com.paisapi.pais_api.dto.UsuarioLoginDTO;
import com.paisapi.pais_api.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioWs {

    @Autowired
    private UsuarioController usuarioController;

    @PostMapping("/autenticar")
    public @ResponseBody UsuarioAutenticadoDTO autenticarUsuario(@RequestBody UsuarioLoginDTO usuarioDetalhes) {
        return usuarioController.autenticarUsuario(usuarioDetalhes);
    }

    @GetMapping("/renovar-ticket")
    public Boolean renovarTicket() {
//        return usuarioController.autenticarUsuario("321", "321");
        return true;
    }

    // Apenas para criar os usuarios ao inicializar
    @PostMapping("/cadastrar")
    public @ResponseBody Usuario cadastrarUsuario(@RequestBody Usuario novoUsuario) {
        return usuarioController.criarUsuario(novoUsuario);
    }


}
