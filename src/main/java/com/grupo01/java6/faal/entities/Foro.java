package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Foro")
@Table(name = "foro", indexes = {
        @Index(name = "fk_foro_estadoForo1_idx", columnList = "estadoForo_id"),
        @Index(name = "fk_foro_login1_idx", columnList = "idLogin")
})
public class Foro implements Serializable {

    @Id
    @Column(name = "idForo", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaCreacion")
    private Instant fechaCreacion;

    @Column(name = "fechaActualizacion")
    private Instant fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEstadoForo", nullable = false)
    private Estadoforo estadoForo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginForo;

}