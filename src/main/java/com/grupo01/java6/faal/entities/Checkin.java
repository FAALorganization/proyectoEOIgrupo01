package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "checkin", schema = "faal", indexes = {
        @Index(name = "fk_checkin_login1_idx", columnList = "idLogin")
})
public class Checkin implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_checkin")
    private Integer id;
    @Column(name = "hora_entrada")
    private Instant horaEntrada;
    @Column(name = "hora_salida")
    private Instant horaSalida;
    @Column(name = "ip")
    private String ip;
    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_login", nullable = false)
    private Login login;

}