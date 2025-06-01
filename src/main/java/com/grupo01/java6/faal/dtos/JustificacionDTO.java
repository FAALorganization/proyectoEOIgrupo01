package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JustificacionDTO {
    private String fecha;
    private String asunto;
    private String descripcion;
    private String archivos;

}
