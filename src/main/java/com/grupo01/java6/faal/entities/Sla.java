package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
/// to do : add unpause time and paude time for later
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private boolean isPaused;
    private LocalDateTime pauseTime;
    private Duration pausedDuration = Duration.ZERO;
    private Duration frozenRemainingTime = null; // duración congelada al pausar

    @Transient
    public Duration getRemainingTime() {
        if (isPaused) {
            // Devuelve la duración que fue congelada al momento de pausar
            return frozenRemainingTime != null ? frozenRemainingTime : Duration.ZERO;
        } else {
            // Calcula tiempo restante tomando en cuenta tiempo pausado acumulado
            return Duration.between(LocalDateTime.now(), deadline.minus(pausedDuration));
        }
    }

    // Método para pausar
    public void pause() {
        if (!isPaused) {
            isPaused = true;
            pauseTime = LocalDateTime.now();
            // Congela el tiempo restante
            frozenRemainingTime = getRemainingTime();
        }
    }

    // Método para reanudar
    public void resume() {
        if (isPaused) {
            isPaused = false;
            Duration timePaused = Duration.between(pauseTime, LocalDateTime.now());
            pausedDuration = pausedDuration.plus(timePaused);
            pauseTime = null;
            frozenRemainingTime = null; // Se borra al reanudar
        }
    }

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticketing ticket;


    public void setPauseStartTime(Object o) {
    }

    public void setTotalPausedDuration(long l) {
    }
}