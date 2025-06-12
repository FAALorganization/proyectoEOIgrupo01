package com.grupo01.java6.faal.dtos;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreateTicketDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 45, message = "El nombre no puede exceder 45 caracteres")
    private String nombre;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 50, message = "El asunto no puede exceder 50 caracteres")
    private String asunto;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;

    @NotBlank(message = "El tipo de ticket es obligatorio")
    private String tipoTicket;

    @NotBlank(message = "La prioridad es obligatoria")
    private String prioridad;
}