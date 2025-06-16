package com.grupo01.java6.faal.dtos;

import com.grupo01.java6.faal.entities.Login;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mensaje2DTO {
    private Integer id;
    private Integer idEmisor;
    private Integer idReceptor;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private Boolean esGrupal;
    private Boolean esLeido;

}
