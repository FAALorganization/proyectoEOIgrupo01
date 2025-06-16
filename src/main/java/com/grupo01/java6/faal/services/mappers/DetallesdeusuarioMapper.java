package com.grupo01.java6.faal.services.mappers;

import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DetallesdeusuarioMapper extends AbstractServiceMapper<Detallesdeusuario, NombreDTO>{
    @Override
    public NombreDTO toDto(Detallesdeusuario entity) {
        return null;
    }

    @Override
    public Detallesdeusuario toEntity(NombreDTO dto) throws Exception {
        Detallesdeusuario entity = new Detallesdeusuario();
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        return entity;
    }
}
