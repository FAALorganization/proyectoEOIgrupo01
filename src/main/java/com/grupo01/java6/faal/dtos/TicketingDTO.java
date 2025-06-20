package com.grupo01.java6.faal.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TicketingDTO {
    private Integer id;
    //@NotBlank(message = "El nombre es obligatorio")
    //@Size(max = 45, message = "El nombre no puede exceder 45 caracteres")
    private String nombre;

    //@NotBlank(message = "El asunto es obligatorio")
   // @Size(max = 50, message = "El asunto no puede exceder 50 caracteres")
    private String asunto;

    //@Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;

    private String tipoTicket;

   private SlaDTO slaDTO;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private  String ticketCategory; //finanzas desarrollo infraestructura
    private Boolean aprobado;

    //@NotBlank(message = "La prioridad es obligatoria")
    private String prioridad;

    // Additional fields for display
    private String usuarioCreador;
    private String usuarioAprobador;
    private String estado;
    @Transient
    private String telefono;
    private String closeReason;
    private LocalDateTime closeDate;
    /// futre implimentation todo

    @Transient // not stored in bd
    private Integer fechaQuejaDay;

    @Transient
    private String fechaQuejaMonth;

    @Transient
    private Integer fechaQuejaYear;

    @Transient      // transient bc m to lazy to modify the entiy so i will do it later  : )
    private String correoGerente;

    public TicketingDTO() {
///  return all the above  constructor
    }

    public String getEstado() {
        if (aprobado == null) return "PENDIENTE";
        return aprobado ? "APROBADO" : "RECHAZADO";
    }

    // calcular el sla
    public Duration calculateSlaDuration() {
        if (slaDTO == null) return Duration.ZERO;
        return Duration.between(slaDTO.getStartTime(),
                slaDTO.getDeadline());
    }

    public String getSlaStatus() {
        if (slaDTO == null) return "NOT_TRACKED";
        if (slaDTO.isPaused()) return "PAUSED";
        if (slaDTO.getRemainingTime().isNegative()) return "BREACHED";
        if (slaDTO.getRemainingTime().toHours() < 4) return "URGENT";
        return "ACTIVE";
    }

    public String getFormattedSlaTime() {
        return slaDTO != null ? slaDTO.getFormattedRemainingTime() : "--:--:--";
    }
}



