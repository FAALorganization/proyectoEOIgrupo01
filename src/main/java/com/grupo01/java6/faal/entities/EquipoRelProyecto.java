package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private static final long serialVersionUID = 6512076782478161478L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idEquipo", nullable = false)
    private Equipo idEquipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idProyecto", nullable = false)
    private Proyecto idProyecto;

    @Column(name = "cometido", length = 100)
    private String cometido;

}