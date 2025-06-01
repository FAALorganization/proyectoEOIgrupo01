package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.TiposAusencias;
import com.grupo01.java6.faal.repositories.TiposAusenciasRepository;
import org.springframework.stereotype.Service;

@Service
public class TiposAusenciasService {
    private final TiposAusenciasRepository tiposAusenciasRepository;

    public TiposAusenciasService(TiposAusenciasRepository tiposAusenciasRepository) {

        this.tiposAusenciasRepository = tiposAusenciasRepository;
    }

    public TiposAusencias getTipoAusenciaById(Integer id) {
        return tiposAusenciasRepository.getTiposAusenciasById(id);
    }
}
