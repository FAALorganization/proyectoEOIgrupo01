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
@Entity(name = "Evento")
@Table(name = "eventos", schema = "faal", indexes = {
        @Index(name = "fk_eventos_login1_idx", columnList = "idLogin")
})
public class Evento implements java.io.Serializable {
    private static final long serialVersionUID = 2695489353375459498L;
    private Integer id;

    private String titulo;

    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDate fechaEliminada;

    private com.grupo01.java6.faal.entities.Login idLogin;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "titulo", length = 45)
    public String getTitulo() {
        return titulo;
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

    @Column(name = "fechaEliminada")
    public LocalDate getFechaEliminada() {
        return fechaEliminada;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdLogin() {
        return idLogin;
    }

}