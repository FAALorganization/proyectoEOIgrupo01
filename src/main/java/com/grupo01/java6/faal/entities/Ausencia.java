package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Ausencia")
@Table(name = "ausencias", schema = "faal", indexes = {
        @Index(name = "fk_ausencias_login1_idx", columnList = "idLogin"),
        @Index(name = "fk_ausencias_tiposAusencias1_idx", columnList = "tiposAusencias_id")
})
public class Ausencia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    @Column(name = "fechaFin")
    private LocalDate fechaFin;
    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "documentos", length = 250)
    private String documentos;
    @Column(name = "calcularDias")
    private Integer calcularDias;
    @Column(name = "aprobado")
    private Boolean aprobado;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLogin;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tiposAusencias_id", nullable = false)
    private Tiposausencia tiposAusencias;



}