package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Sla")
@Table(name = "sla", schema = "faal", indexes = {
        @Index(name = "id_prior", columnList = "id_prior")
})
public class Sla implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSla", nullable = false)
    private Integer id;

    @Column(name = "prioridades_enum", nullable = false, length = 100)
    private String prioridadesEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_prior")
    private Prioridades idPrior;

    @Column(name = "num_horas")
    private Double numHoras;

    @Column(name = "fecha_ini")
    private Instant fechaIni;

    @Column(name = "fecha_fin")
    private Instant fechaFin;

}