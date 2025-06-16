package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AusenciaDTO {
    private Integer tipoAusenciaId;
    private Boolean aprobado;
    private String fechaInicio;
    private String fechaFin;
    private Boolean justificada;

}

