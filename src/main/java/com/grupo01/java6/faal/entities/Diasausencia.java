package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Diasausencia")
@Table(name = "diasausencias", schema = "faal", indexes = {
        @Index(name = "fk_diasAusencias_ausencias1_idx", columnList = "ausencias_id")
})
public class Diasausencia implements java.io.Serializable {
    private static final long serialVersionUID = 5024956695981872640L;
    private Integer id;

    private Integer diasTotales;

    private Integer diasDisfrutados;

    private Ausencia ausencias;

    @Id
    @Column(name = "idDiasAusencias", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "diasTotales")
    public Integer getDiasTotales() {
        return diasTotales;
    }

    @Column(name = "diasDisfrutados")
    public Integer getDiasDisfrutados() {
        return diasDisfrutados;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ausencias_id", nullable = false)
    public Ausencia getAusencias() {
        return ausencias;
    }

}