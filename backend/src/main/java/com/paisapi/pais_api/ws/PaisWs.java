package com.paisapi.pais_api.ws;

import com.paisapi.pais_api.controller.PaisController;
import com.paisapi.pais_api.model.Pais;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pais")
public class PaisWs {

    @Autowired
    private PaisController paisController;

    @Operation(summary = "Lista todos os países existentes")
    @GetMapping("/listar")
    public @ResponseBody List<Pais> listarPaises() {
        return paisController.getAllPaises();
    }

    @Operation(summary = "Cadastra ou edita um país")
    @PostMapping("/salvar")
    public @ResponseBody Pais criarPais(@RequestBody Pais paisBody) {
        return paisController.novoPais(paisBody);
    }

    @Operation(summary = "Pesquisa os países de acordo com o filtro")
    @GetMapping("/pesquisar")
    public @ResponseBody List<Pais> buscarPaises(@RequestParam String filtro) {
        return paisController.getPaisesFiltrados(filtro);
    }

    @Operation(summary = "Exclui um país pelo id")
    @GetMapping("/excluir")
    public Boolean excluirPais(@RequestParam Long id) {
        return paisController.deletarPais(id);
    }
}
