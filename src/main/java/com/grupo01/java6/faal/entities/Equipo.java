package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Equipo")
@Table(name = "equipo", schema = "faal", indexes = {
        @Index(name = "fk_equipo_grupo_chat1_idx", columnList = "idGrupoChat")
})
public class Equipo implements java.io.Serializable {
    private static final long serialVersionUID = -3041763843574778840L;
    private Integer id;

    private String nombre;

    private String descripcion;

    private com.grupo01.java6.faal.entities.GrupoChat idGrupoChat;

    @Id
    @Column(name = "idEquipo", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "nombre", nullable = false, length = 100)
    public String getNombre() {
        return nombre;
    }

    @Lob
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGrupoChat", nullable = false)
    public com.grupo01.java6.faal.entities.GrupoChat getIdGrupoChat() {
        return idGrupoChat;
    }

}