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
@ToString
@Entity(name = "Tarea")
@Table(name = "tareas", schema = "faal", indexes = {
        @Index(name = "fk_tareas_login1_idx", columnList = "idLogin")
})
public class Tarea implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "titulo", length = 50)

    private String titulo;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipoTarea", length = 45)

    private String tipoTarea;
    @Column(name = "fechaInicio")

    private LocalDate fechaInicio;
    @Column(name = "fechaFin")

    private LocalDate fechaFin;
    @Column(name = "fechaEliminada")

    private LocalDate fechaEliminada;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLogin;





}