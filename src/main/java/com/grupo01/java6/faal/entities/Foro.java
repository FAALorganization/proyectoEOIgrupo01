package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "foro", schema = "faal", indexes = {
        @Index(name = "fk_foro_estadoForo1_idx", columnList = "estadoForo_id"),
        @Index(name = "fk_foro_login1_idx", columnList = "idLogin")
})
public class Foro implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foro", nullable = false)
    private Integer id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;
    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estadoForo_id", nullable = false)
    private Estadoforo estadoForo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login login;

    @OneToMany(mappedBy = "foro", fetch = FetchType.LAZY)
    private List<Post> posts;

}