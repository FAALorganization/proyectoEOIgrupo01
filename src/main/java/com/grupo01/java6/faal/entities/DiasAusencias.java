package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Diasausencia")
@Table(name = "diasausencias", schema = "faal", indexes = {
        @Index(name = "fk_diasAusencias_ausencias1_idx", columnList = "ausencias_id")
})
public class DiasAusencias implements Serializable {

    @Id
    @Column(name = "idDiasAusencias", nullable = false)
    private Integer id;

    @Column(name = "diasTotales")
    private Integer diasTotales;

    @Column(name = "diasDisfrutados")
    private Integer diasDisfrutados;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ausencias_id", nullable = false)
    private Ausencias diasAusencias;

}