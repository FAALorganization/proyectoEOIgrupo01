package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Tipos_Rel_Evento")
@Table(name = "tipos_rel_eventos", schema = "faal", indexes = {
        @Index(name = "fk_tipoEventos_has_eventos_tipoEventos1_idx", columnList = "tipoEventos_id"),
        @Index(name = "fk_tipoEventos_has_eventos_eventos1_idx", columnList = "eventos_id")
})
public class TiposRelEvento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipoEventos_id", nullable = false)
    private Tipoevento tipoEventos;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eventos_id", nullable = false)
    private Evento eventos;



}