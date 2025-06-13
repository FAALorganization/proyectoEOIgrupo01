package com.grupo01.java6.faal.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketingDTO {
    private Integer id;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    private Boolean aprobado;

    @NotBlank(message = "La prioridad es obligatoria")
    private String prioridad;

    // Additional fields for display
    private String usuarioCreador;
    private String usuarioAprobador;
    private String estado;
    @Transient
    private String telefono;
    @Transient
    private LocalDate fechaQueja ;
    @Transient      // transient bc m to lazy to modify the entiy so i will do it later  : ) 
    private String correoGerente;
    public String getEstado() {
        if (aprobado == null) return "PENDIENTE";
        return aprobado ? "APROBADO" : "RECHAZADO";
    }
}



