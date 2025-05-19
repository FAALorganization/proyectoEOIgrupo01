package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tareas", schema = "faal", indexes = {
        @Index(name = "fk_tareas_login1_idx", columnList = "idLogin")
})
public class Tarea implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipoTarea")
    private String tipoTarea;
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    @Column(name = "fechaFin")
    private LocalDate fechaFin;
    @Column(name = "fechaEliminada")
    private LocalDate fechaEliminada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login login;

    @OneToMany(mappedBy = "tareas", fetch = FetchType.LAZY)
    private List<EstadosRelTarea> estadosRelTareas;
}