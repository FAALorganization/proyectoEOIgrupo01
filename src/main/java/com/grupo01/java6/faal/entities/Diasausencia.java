package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "diasausencias", schema = "faal", indexes = {
        @Index(name = "fk_diasAusencias_ausencias1_idx", columnList = "ausencias_id")
})
public class Diasausencia implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_ausencia")
    private Integer id;
    @Column(name = "dias_totales")
    private Integer diasTotales;
    @Column(name = "dias_disfrutados")
    private Integer diasDisfrutados;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ausencias_id", nullable = false)
    private Ausencia ausencias;


}