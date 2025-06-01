package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoConAusenciasDTO {
    private String nombre;
    private String apellidos;
    private List<RangoAusenciaDTO> ausencias = new ArrayList<>();

    public void agregarAusencia(LocalDate inicio, LocalDate fin) {
        this.ausencias.add(new RangoAusenciaDTO(inicio, fin));
    }

    public EmpleadoConAusenciasDTO(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.ausencias = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangoAusenciaDTO {
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
    }
}
