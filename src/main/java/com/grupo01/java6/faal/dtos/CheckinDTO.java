package com.grupo01.java6.faal.dtos;

import com.grupo01.java6.faal.entities.TipoCheckin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CheckinDTO {
    private Integer id;
    private String horaEntrada; // Cambiado a String
    private String horaSalida;  // Cambiado a String
    private LocalDate fecha;
    private String ip;
    private TipoCheckin tipo;

    // Constructor vacío
    public CheckinDTO() {}

    // Constructor con todos los campos que quieras mostrar
    public CheckinDTO(Integer id, LocalTime horaEntrada, LocalTime horaSalida, LocalDate fecha, String ip, TipoCheckin tipo) {
        this.id = id;
        this.horaEntrada = horaEntrada != null ? horaEntrada.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null;
        this.horaSalida = horaSalida != null ? horaSalida.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null;
        this.fecha = fecha;
        this.ip = ip;
        this.tipo = tipo;
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public TipoCheckin getTipo() { return tipo; }
    public void setTipo(TipoCheckin tipo) { this.tipo = tipo; }

    // Método para formatear la fecha
    public String getFechaFormatted() {
        if (fecha != null) {
            return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "";
    }
}