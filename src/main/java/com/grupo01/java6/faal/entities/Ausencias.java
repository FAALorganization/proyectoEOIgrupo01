package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ausencias",schema = "faal", indexes = {
        @Index(name = "fk_ausencias_login1_idx", columnList = "idLogin"),
        @Index(name = "fk_ausencias_tiposAusencias1_idx", columnList = "tiposAusencias_id")
})
public class Ausencias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ausencia")
    private Integer id;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "justificacion")
    private String justificacion;

    @Column(name = "documentos", length = 250)
    private String documentos;

    @Column(name = "calcular_dias")
    private Integer calcularDias;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginAusencias;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idTiposAusencias", nullable = false)
    private TiposAusencias tiposAusencias;

}