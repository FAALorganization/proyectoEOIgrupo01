package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketingDTO {
    private Integer id;
    private String nombre;
    private String asunto;
    private String descripcion;
    private String tipoTicket;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean aprobado;
    private String prioridad;


}
