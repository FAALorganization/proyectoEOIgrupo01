package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
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
    @Column(name = "id_Sla", nullable = false)
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

    private Instant slaStartTime;
    // When SLA timer started or resumed
    private Duration slaElapsed = Duration.ZERO; // Total elapsed time before pause
    private boolean slaPaused = true;
    /*
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticketing ticket;

     */


}