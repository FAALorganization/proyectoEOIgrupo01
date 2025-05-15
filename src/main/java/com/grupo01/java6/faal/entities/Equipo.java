package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Equipo")
@Table(name = "equipo", schema = "faal", indexes = {
        @Index(name = "fk_equipo_grupo_chat1_idx", columnList = "idGrupoChat")
})
public class Equipo implements Serializable {
    private static final long serialVersionUID = -3041763843574778840L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEquipo", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGrupoChat", nullable = false)
    private GrupoChat idGrupoChat;

}