package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubordinadoDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String tlf;
}

