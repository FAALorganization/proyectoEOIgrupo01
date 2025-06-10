// Archivo: DetallesdeusuarioService.java
package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.repositories.DetallesDeUsuarioRepository;
import com.grupo01.java6.faal.services.mappers.DetallesdeusuarioMapper;
import org.springframework.stereotype.Service;
import com.grupo01.java6.faal.dtos.NombreDTO;

import java.util.List;

@Service
public class DetallesdeusuarioService extends AbstractBusinessService<Detallesdeusuario, Integer, NombreDTO, DetallesDeUsuarioRepository, DetallesdeusuarioMapper> {

    private final DetallesDeUsuarioRepository detallesdeusuarioRepository;

    public DetallesdeusuarioService(DetallesDeUsuarioRepository repo, DetallesdeusuarioMapper mapper) {
        super(repo, mapper);
        this.detallesdeusuarioRepository = repo;
    }

    public Detallesdeusuario findByNombreAndApellidos(String nombre, String apellidos) {
        Detallesdeusuario detalles = detallesdeusuarioRepository.findByNombreAndApellidos(nombre, apellidos);
        if (detalles == null) {
            throw new IllegalArgumentException("Usuario no encontrado con nombre y apellidos: " + nombre + " " + apellidos);
        }
        return detalles;
    }

    public boolean verificarRolJefe(Integer idUsuario) {
        String rol = detallesdeusuarioRepository.findRolByUsuarioId(idUsuario);
        return "JEFE".equalsIgnoreCase(rol);
    }

    public List<Detallesdeusuario> obtenerTodosLosUsuarios() {
        return detallesdeusuarioRepository.findAll();
    }

    public List<Detallesdeusuario> obtenerUsuariosActivos() {
        return detallesdeusuarioRepository.findUsuariosActivos();
    }
}