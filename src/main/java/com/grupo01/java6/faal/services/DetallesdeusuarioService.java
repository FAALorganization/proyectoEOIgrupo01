package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.repositories.DetallesDeUsuarioRepository;
import com.grupo01.java6.faal.services.mappers.DetallesdeusuarioMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DetallesdeusuarioService extends AbstractBusinessService<Detallesdeusuario,Integer, NombreDTO,DetallesDeUsuarioRepository, DetallesdeusuarioMapper>{

    public DetallesdeusuarioService(DetallesDeUsuarioRepository repo, DetallesdeusuarioMapper mapper) {
        super(repo,mapper);
    }

}
