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
@Entity(name = "Tarea")
@Table(name = "tareas", schema = "faal", indexes = {
        @Index(name = "fk_tareas_login1_idx", columnList = "idLogin")
})
public class Tarea implements java.io.Serializable {
    private static final long serialVersionUID = -2185622260201338628L;
    private Integer id;

    private String titulo;

    private String descripcion;

    private String tipoTarea;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDate fechaEliminada;

    private Login idLogin;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "titulo", length = 50)
    public String getTitulo() {
        return titulo;
    }

    @Lob
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @Column(name = "tipoTarea", length = 45)
    public String getTipoTarea() {
        return tipoTarea;
    }

    @Column(name = "fechaInicio")
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    @Column(name = "fechaFin")
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Column(name = "fechaEliminada")
    public LocalDate getFechaEliminada() {
        return fechaEliminada;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public Login getIdLogin() {
        return idLogin;
    }

}