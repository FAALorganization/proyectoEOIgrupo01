package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estados_rel_tareas", schema = "faal", indexes = {
        @Index(name = "fk_estadosTareas_has_tareas_estadosTareas1_idx", columnList = "estadosTareas_id"),
        @Index(name = "fk_estadosTareas_has_tareas_tareas1_idx", columnList = "tareas_id")
})
public class EstadosRelTarea implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estados_tareas_id", nullable = false)
    private Estadostarea estadosTareas;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tareas_id", nullable = false)
    private Tarea tareas;


}