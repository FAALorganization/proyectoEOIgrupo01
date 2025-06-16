package com.grupo01.java6.faal.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeDTO {
    private Integer emisorId;
    private Integer receptorId; // null si es grupal
    private String contenido;
    private Boolean esGrupal;
}
