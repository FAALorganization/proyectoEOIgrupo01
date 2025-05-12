package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Checkin")
@Table(name = "checkin", schema = "faal", indexes = {
        @Index(name = "fk_checkin_login1_idx", columnList = "idLogin")
})
public class Checkin implements java.io.Serializable {
    private static final long serialVersionUID = -1162796386140223742L;
    private Integer id;

    private Instant horaEntrada;

    private Instant horaSalida;

    private String ip;

    private Instant updateDate;

    private com.grupo01.java6.faal.entities.Login idLogin;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "horaEntrada")
    public Instant getHoraEntrada() {
        return horaEntrada;
    }

    @Column(name = "horaSalida")
    public Instant getHoraSalida() {
        return horaSalida;
    }

    @Column(name = "ip", length = 250)
    public String getIp() {
        return ip;
    }

    @Column(name = "updateDate")
    public Instant getUpdateDate() {
        return updateDate;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdLogin() {
        return idLogin;
    }

}