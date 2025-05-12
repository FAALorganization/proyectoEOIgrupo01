package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Sla")
@Table(name = "sla", schema = "faal", indexes = {
        @Index(name = "id_prior", columnList = "id_prior")
})
public class Sla implements java.io.Serializable {
    private static final long serialVersionUID = 4689274710532741418L;
    private Integer id;

    private String prioridadesEnum;

    private Prioridade idPrior;

    private Double numHoras;

    private Instant fechaIni;

    private Instant fechaFin;

    @Id
    @Column(name = "idSla", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "prioridades_enum", nullable = false, length = 100)
    public String getPrioridadesEnum() {
        return prioridadesEnum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_prior")
    public Prioridade getIdPrior() {
        return idPrior;
    }

    @Column(name = "num_horas")
    public Double getNumHoras() {
        return numHoras;
    }

    @Column(name = "fecha_ini")
    public Instant getFechaIni() {
        return fechaIni;
    }

    @Column(name = "fecha_fin")
    public Instant getFechaFin() {
        return fechaFin;
    }

}