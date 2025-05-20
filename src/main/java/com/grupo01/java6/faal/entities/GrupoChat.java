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

    @OneToMany(mappedBy = "grupoChatUsuario", fetch = FetchType.LAZY)
    private List<GrupoChatUsuario> listaGruposChatUsuario;

    @OneToMany(mappedBy = "idGrupoChat", fetch = FetchType.LAZY)
    private List<Equipo> equipos;

    @OneToMany(mappedBy = "idGrupo", fetch = FetchType.LAZY)
    private List<MensajeGrupo> mensajesGrupos;

}