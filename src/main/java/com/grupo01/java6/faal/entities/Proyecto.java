package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Proyecto")
@Table(name = "proyecto", schema = "faal")
public class Proyecto implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "idProyecto", nullable = false)
    private Integer id;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    @Column(name = "fechaFin")
    private LocalDate fechaFin;


}