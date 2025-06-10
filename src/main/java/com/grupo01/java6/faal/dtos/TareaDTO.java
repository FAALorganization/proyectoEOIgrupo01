package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TareaDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaEliminada;
    private LocalDate fechaLimite;
    private Integer tipoTarea;
    private String estado;
    private String titulo;
    private String descripcion;
}

