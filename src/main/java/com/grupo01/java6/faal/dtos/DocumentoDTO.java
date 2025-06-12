package com.grupo01.java6.faal.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {
    private Long id;
    private String nombre;

    public DocumentoDTO() {
    }

    public DocumentoDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
