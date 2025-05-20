package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grupo_chat", schema = "faal", indexes = {
        @Index(name = "fk_grupo_chat_login1_idx", columnList = "idLogin")
})
public class GrupoChat implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "es_equipo")
    private Boolean esEquipo;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEquipo", nullable = false)
    private Equipo idEquipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginGrupoChat;

}