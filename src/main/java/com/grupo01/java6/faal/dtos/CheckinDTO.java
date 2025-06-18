package com.grupo01.java6.faal.dtos;

import com.grupo01.java6.faal.entities.TipoCheckin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class CheckinDTO {
    private Integer id;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private LocalDate fecha;
    private String ip;
    private TipoCheckin tipo;

    // ---- MÉTODOS ADICIONALES PARA FORMATEAR ----
    public String getHoraEntradaFormatted() {
        return horaEntrada != null ? horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";
    }

    public String getHoraSalidaFormatted() {
        return horaSalida != null ? horaSalida.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";
    }

    public String getFechaFormatted() {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }
}