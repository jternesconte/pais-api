package com.paisapi.pais_api.controller;

import com.paisapi.pais_api.dto.PaisDTO;
import com.paisapi.pais_api.model.Pais;
import com.paisapi.pais_api.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
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

    public Pais novoPais(Pais paisInfo) {
        if(!usuarioController.isAdministrador()) {

        }

        if(paisInfo.getId() == 0) {
            List<Pais> paises = getAllPaises();

            for (Pais pais : paises) {
                if(Objects.equals(paisInfo.getNome(), pais.getNome())) {
                    return null;
                } else if(Objects.equals(paisInfo.getSigla(), pais.getSigla())) {
                    return null;
                } else if(Objects.equals(paisInfo.getGentilico(), pais.getGentilico())) {
                    return null;
                }
            }

            Pais novoPais = new Pais(
                    paisInfo.getNome(),
                    paisInfo.getSigla(),
                    paisInfo.getGentilico()
            );

            return paisRepository.save(novoPais);
        } else {
            Optional<Pais> paisExistente = paisRepository.findById(paisInfo.getId());

            if(!paisExistente.isEmpty()){
                paisExistente.get().setNome(paisInfo.getNome());
                paisExistente.get().setSigla(paisInfo.getSigla());
                paisExistente.get().setGentilico(paisInfo.getGentilico());
            }

            return paisRepository.save(paisExistente.get());
        }
    }

    public List<Pais> getPaisesFiltrados(String filtro) {
        return (List<Pais>) paisRepository.findByNomeContaining(filtro);
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
