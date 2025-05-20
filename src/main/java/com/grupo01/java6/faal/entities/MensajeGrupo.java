package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mensaje_grupo", schema = "faal", indexes = {
        @Index(name = "fk_mensaje_grupo_login1_idx", columnList = "idAutor"),
        @Index(name = "fk_mensaje_grupo_grupo_chat1_idx", columnList = "idGrupo")
})
public class MensajeGrupo implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje", nullable = false)
    private Integer id;
    @Column(name = "mensaje", nullable = false)
    private String mensaje;
    @Column(name = "fecha_envio")
    private Instant fechaEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_autor", nullable = false)
    private Login idAutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_grupo", nullable = false)
    private GrupoChat idGrupo;
}