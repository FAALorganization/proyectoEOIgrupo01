package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Diasausencia")
@Table(name = "diasausencias", schema = "faal", indexes = {
        @Index(name = "fk_diasAusencias_ausencias1_idx", columnList = "ausencias_id")
})
public class Diasausencia implements Serializable {
    private static final long serialVersionUID = 5024956695981872640L;

    @Id
    @Column(name = "idDiasAusencias", nullable = false)
    private Integer id;

    @Column(name = "diasTotales")
    private Integer diasTotales;

    @Column(name = "diasDisfrutados")
    private Integer diasDisfrutados;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ausencias_id", nullable = false)
    private Ausencia ausencias;

}