package com.grupo01.java6.faal.dtos;

import com.grupo01.java6.faal.entities.TiposAusencias;
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
    private String token;
    private List<RangoAusenciaDTO> ausencias = new ArrayList<>();

    public void agregarAusencia(LocalDate inicio, LocalDate fin, boolean aprobado, TiposAusencias tipoAusencia, String justificacion) {
        this.ausencias.add(new RangoAusenciaDTO(inicio, fin, aprobado, tipoAusencia, justificacion));
    }

    public EmpleadoConAusenciasDTO(String nombre, String apellidos, String token) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.token = token;
        this.ausencias = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RangoAusenciaDTO {
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private boolean aprobado;
        private TiposAusencias tipoAusencias;
        private String justificacion;
    }
}
