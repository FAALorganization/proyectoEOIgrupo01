package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalTime horaEntrada;

    @Column(name = "horaSalida")
    private LocalTime horaSalida;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "ip", length = 250)
    private String ip;

    @Column(name = "updateDate")
    private LocalDate updateDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLoginCheckin;

    // Este es el nuevo campo enum
    @Enumerated(EnumType.STRING) // Guardamos el enum como texto en la BD
    @Column(name = "tipo", nullable = false)
    private TipoCheckin tipo;

    @Transient
    private String fechaFormatted;

    public String getFechaFormatted() {
        return fechaFormatted;
    }

    public void setFechaFormatted(String fechaFormatted) {
        this.fechaFormatted = fechaFormatted;
    }


}