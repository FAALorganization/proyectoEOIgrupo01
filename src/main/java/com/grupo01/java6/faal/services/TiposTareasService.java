package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.TipoTareas;
import com.grupo01.java6.faal.repositories.TiposTareasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class TiposTareasService {
    private final TiposTareasRepository tiposTareasRepository;

    public TiposTareasService(TiposTareasRepository tiposTareasRepository) {
        this.tiposTareasRepository = tiposTareasRepository;
    }

    public List<TipoTareas> findAll(){
        return tiposTareasRepository.findAll();
    }
}
