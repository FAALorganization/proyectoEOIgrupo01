package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.DetallesDeUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DetallesdeusuarioService {
    private final DetallesDeUsuarioRepository detallesDeUsuarioRepository;

    public DetallesdeusuarioService(DetallesDeUsuarioRepository detallesDeUsuarioRepository) {
        this.detallesDeUsuarioRepository = detallesDeUsuarioRepository;
    }

}
