package com.grupo01.java6.faal.dtos;

import com.grupo01.java6.faal.entities.TiposAusencias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NombreConAusenciasDTO {
    private String nombre;
    private String apellidos;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean aprobado;
    private TiposAusencias idAusencia;
    private String justificacion;
}
