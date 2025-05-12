package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Foro")
@Table(name = "foro", schema = "faal", indexes = {
        @Index(name = "fk_foro_estadoForo1_idx", columnList = "estadoForo_id"),
        @Index(name = "fk_foro_login1_idx", columnList = "idLogin")
})
public class Foro implements java.io.Serializable {
    private static final long serialVersionUID = 969672448080589464L;
    private Integer id;

    private Estadoforo estadoForo;

    private String titulo;

    private String descripcion;

    private Instant fechaCreacion;

    private Instant fechaActualizacion;

    private com.grupo01.java6.faal.entities.Login idLogin;

    @Id
    @Column(name = "idForo", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estadoForo_id", nullable = false)
    public Estadoforo getEstadoForo() {
        return estadoForo;
    }

    @Column(name = "titulo", nullable = false, length = 100)
    public String getTitulo() {
        return titulo;
    }

    @Lob
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaCreacion")
    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    @Column(name = "fechaActualizacion")
    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdLogin() {
        return idLogin;
    }

}