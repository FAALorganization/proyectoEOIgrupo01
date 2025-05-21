package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Sla")
@Table(name = "sla", indexes = {
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
    @JoinColumn(name = "id_prior")
    private Prioridades idPrior;

    @Column(name = "num_horas")
    private Double numHoras;

    @Column(name = "fecha_ini")
    private LocalDate fechaIni;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

}