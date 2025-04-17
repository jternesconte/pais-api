package com.paisapi.pais_api.ws;

import com.paisapi.pais_api.controller.PaisController;
import com.paisapi.pais_api.model.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pais")
public class PaisWs {

    @Autowired
    private PaisController paisController;

    @GetMapping("/listar")
    public @ResponseBody List<Pais> listarPaises() {
        return paisController.getAllPaises();
    }

    @PostMapping("/salvar")
    public @ResponseBody Pais criarPais(@RequestBody Pais paisBody) {
        return paisController.novoPais(paisBody);
    }

    @GetMapping("/pesquisar")
    public @ResponseBody List<Pais> buscarPaises(@RequestBody String filtro) {
        return paisController.getPaisesFiltrados(filtro);
    }

    @GetMapping("/excluir")
    public Boolean excluirPais(@RequestBody Long paisId) {
        return paisController.deletarPais(paisId);
    }
}
