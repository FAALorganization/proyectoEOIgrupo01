package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "equipo", schema = "faal", indexes = {
        @Index(name = "fk_equipo_grupo_chat1_idx", columnList = "idGrupoChat")
})
public class Equipo implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_grupo_chat", nullable = false)
    private GrupoChat idGrupoChat;

    @OneToMany(mappedBy = "equipoProyecto", fetch = FetchType.LAZY)
    private List<EquipoRelProyecto> equiposProyecto;

    @OneToMany(mappedBy = "equipoLogin", fetch = FetchType.LAZY)
    private List<LoginEquipo> equiposLogin;


}