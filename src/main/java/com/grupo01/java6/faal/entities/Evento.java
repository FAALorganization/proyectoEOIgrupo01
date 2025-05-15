package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Evento")
@Table(name = "eventos", schema = "faal", indexes = {
        @Index(name = "fk_eventos_login1_idx", columnList = "idLogin")
})
public class Evento implements Serializable {
    private static final long serialVersionUID = 2695489353375459498L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", length = 45)
    private String titulo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    @Column(name = "fechaEliminada")
    private LocalDate fechaEliminada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLogin;

}