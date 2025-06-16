package com.grupo01.java6.faal.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProyectoDTO {
    private Integer id;  // Coincide con la entidad Proyecto
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;  // Añadido
    private LocalDate fechaFin;     // Añadido
    private List<DocumentoDTO> documentos;

    public ProyectoDTO() {
    }

    public ProyectoDTO(Integer id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, List<DocumentoDTO> documentos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.documentos = documentos;
    }
}
