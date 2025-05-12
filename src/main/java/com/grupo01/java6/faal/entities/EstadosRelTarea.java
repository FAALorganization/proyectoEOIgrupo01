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
@Entity(name = "Estados_Rel_Tarea")
@Table(name = "estados_rel_tareas", schema = "faal", indexes = {
        @Index(name = "fk_estadosTareas_has_tareas_estadosTareas1_idx", columnList = "estadosTareas_id"),
        @Index(name = "fk_estadosTareas_has_tareas_tareas1_idx", columnList = "tareas_id")
})
public class EstadosRelTarea implements java.io.Serializable {
    private static final long serialVersionUID = 2453648775063021147L;
    private Integer id;

    private com.grupo01.java6.faal.entities.Estadostarea estadosTareas;

    private com.grupo01.java6.faal.entities.Tarea tareas;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estadosTareas_id", nullable = false)
    public com.grupo01.java6.faal.entities.Estadostarea getEstadosTareas() {
        return estadosTareas;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tareas_id", nullable = false)
    public com.grupo01.java6.faal.entities.Tarea getTareas() {
        return tareas;
    }

}