package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.DocumentoDTO;
import com.grupo01.java6.faal.dtos.ProyectoDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    @Autowired
    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<Proyecto> buscarPorNombreYUsuario(String nombre, Login usuario) {
        return proyectoRepository.findByNombreContainingIgnoreCaseAndUsuarios(nombre, usuario);
    }

    public ProyectoDTO convertirAProyectoDTO(Proyecto proyecto) {
        List<DocumentoDTO> documentosDTO = proyecto.getDocumentos().stream()
                .map(doc -> new DocumentoDTO(doc.getId(), doc.getNombre()))
                .collect(Collectors.toList());

        return new ProyectoDTO(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getFechaInicio(),
                proyecto.getFechaFin(),
                documentosDTO
        );
    }

    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }

    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public List<ProyectoDTO> obtenerProyectosDTO() {
        return proyectoRepository.findAll().stream()
                .map(this::convertirAProyectoDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProyectoDTO> buscarProyectosDTOporNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertirAProyectoDTO)
                .collect(Collectors.toList());
    }
}
