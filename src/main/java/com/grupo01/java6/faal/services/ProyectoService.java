package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.DocumentoDTO;
import com.grupo01.java6.faal.dtos.ProyectoDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    // Obtener todos los proyectos con documentos y convertir a DTO
    @Transactional(readOnly = true)
    public List<ProyectoDTO> obtenerProyectosDTO() {
        List<Proyecto> proyectos = proyectoRepository.findAllConDocumentos(); // Trae proyectos con documentos
        return proyectos.stream()
                .map(this::convertirAProyectoDTO)
                .collect(Collectors.toList());
    }

    // Buscar proyectos por nombre (sin filtrar por usuario) y convertir a DTO
    @Transactional(readOnly = true)
    public List<ProyectoDTO> buscarProyectosDTOporNombre(String nombre) {
        List<Proyecto> proyectos = proyectoRepository.findByNombreContainingIgnoreCase(nombre);
        return proyectos.stream()
                .map(this::convertirAProyectoDTO)
                .collect(Collectors.toList());
    }

    // Buscar proyectos por nombre y usuario (si lo necesitas para permisos)
    @Transactional(readOnly = true)
    public List<Proyecto> buscarPorNombreYUsuario(String nombre, Login usuario) {
        return proyectoRepository.findByNombreContainingIgnoreCaseAndUsuarios(nombre, usuario);
    }

    // Conversión entidad -> DTO (incluye documentos convertidos a DTO)
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
}
