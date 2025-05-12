package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Equipo_Rel_Proyecto")
@Table(name = "equipo_rel_proyecto", schema = "faal", indexes = {
        @Index(name = "idProyecto", columnList = "idProyecto")
})
public class EquipoRelProyecto implements java.io.Serializable {
    private static final long serialVersionUID = 6512076782478161478L;
    private Integer id;

    private Equipo idEquipo;

    private com.grupo01.java6.faal.entities.Proyecto idProyecto;

    private String cometido;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idEquipo", nullable = false)
    public Equipo getIdEquipo() {
        return idEquipo;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idProyecto", nullable = false)
    public com.grupo01.java6.faal.entities.Proyecto getIdProyecto() {
        return idProyecto;
    }

    @Column(name = "cometido", length = 100)
    public String getCometido() {
        return cometido;
    }

}