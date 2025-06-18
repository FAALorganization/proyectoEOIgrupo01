package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Tarea")
@Table(name = "tareas", indexes = {
        @Index(name = "fk_tareas_login1_idx", columnList = "idLogin")
})
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", length = 50)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "tipo_tareas", referencedColumnName = "id")
    private TipoTareas tipoTarea;

    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    @Column(name = "fechaEliminada")
    private LocalDate fechaEliminada;

    @Column(name = "fechaLimite")
    private LocalDate fechaLimite;

    @Column(name = "estado", length = 20, nullable = false)
    private String estado = "pendiente"; // Valor por defecto

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginTarea;

}