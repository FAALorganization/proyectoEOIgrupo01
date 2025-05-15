package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Checkin")
@Table(name = "checkin", schema = "faal", indexes = {
        @Index(name = "fk_checkin_login1_idx", columnList = "idLogin")
})
public class Checkin implements Serializable {
    private static final long serialVersionUID = -1162796386140223742L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "horaEntrada")
    private Instant horaEntrada;

    @Column(name = "horaSalida")
    private Instant horaSalida;

    @Column(name = "ip", length = 250)
    private String ip;

    @Column(name = "updateDate")
    private Instant updateDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLogin;

}