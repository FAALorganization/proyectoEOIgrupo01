package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Equipo_Rel_Proyecto")
@Table(name = "equipo_rel_proyecto", schema = "faal", indexes = {
        @Index(name = "idProyecto", columnList = "idProyecto")
})
public class EquipoRelProyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cometido", length = 100)
    private String cometido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEquipo", nullable = false)
    private Equipo equipoEquipoRelProyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProyecto", nullable = false)
    private Proyecto proyectoEquipoRelProyecto;

}