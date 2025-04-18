package com.paisapi.pais_api.controller;

import com.paisapi.pais_api.dto.PaisDTO;
import com.paisapi.pais_api.model.Pais;
import com.paisapi.pais_api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PaisController {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private UsuarioController usuarioController;

    public List<Pais> getAllPaises() {
        return (List<Pais>) paisRepository.findAll();
    }

    public Pais novoPais(PaisDTO paisInfo) {
        if(!usuarioController.isAdministrador()) {

        }

        Pais pais = new Pais(
                paisInfo.getNome(),
                paisInfo.getSigla(),
                paisInfo.getGentilico()
        );

        return paisRepository.save(pais);
    }

    public List<Pais> getPaisesFiltrados(String filtro) {
        return (List<Pais>) paisRepository.findByNomeContainingIgnoreCase(filtro);
    }

    public Boolean deletarPais(Long paisId) {
        Optional<Pais> paisExcluido = paisRepository.findById(paisId);
        boolean isPaisExistente = !paisExcluido.isEmpty();


        paisRepository.deleteById(paisId);
        if(isPaisExistente) {
            return true;
        } else {
            return false;
        }
    }
}
