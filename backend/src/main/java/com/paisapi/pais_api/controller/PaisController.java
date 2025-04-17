package com.paisapi.pais_api.controller;

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

    public List<Pais> getAllPaises() {
        return (List<Pais>) paisRepository.findAll();
    }

    public Pais novoPais(Pais paisInfo) {

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
        paisRepository.deleteById(paisId);

        Optional<Pais> paisExcluido = paisRepository.findById(paisId);

        if(paisExcluido.isEmpty()) {
            return false;
        } else {
        return true;
        }
    }
}
