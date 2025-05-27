package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Checkin")
@Table(name = "checkin", indexes = {
        @Index(name = "fk_checkin_login1_idx", columnList = "idLogin")
})
public class Checkin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "horaEntrada")
    private LocalDate horaEntrada;

    @Column(name = "horaSalida")
    private LocalDate horaSalida;

    @Column(name = "ip", length = 250)
    private String ip;

    @Column(name = "updateDate")
    private LocalDate updateDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLoginCheckin;

}