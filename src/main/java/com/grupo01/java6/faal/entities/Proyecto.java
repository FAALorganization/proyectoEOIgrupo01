package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Proyecto")
@Table(name = "proyecto", schema = "faal")
public class Proyecto implements java.io.Serializable {
    private static final long serialVersionUID = 946245792733032785L;
    private Integer id;

    private String nombre;

    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Id
    @Column(name = "idProyecto", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "nombre", nullable = false, length = 100)
    public String getNombre() {
        return nombre;
    }

    @Lob
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @Column(name = "fechaInicio")
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    @Column(name = "fechaFin")
    public LocalDate getFechaFin() {
        return fechaFin;
    }

}