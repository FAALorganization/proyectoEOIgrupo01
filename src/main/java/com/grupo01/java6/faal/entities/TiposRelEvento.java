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
@Entity(name = "Tipos_Rel_Evento")
@Table(name = "tipos_rel_eventos", schema = "faal", indexes = {
        @Index(name = "fk_tipoEventos_has_eventos_tipoEventos1_idx", columnList = "tipoEventos_id"),
        @Index(name = "fk_tipoEventos_has_eventos_eventos1_idx", columnList = "eventos_id")
})
public class TiposRelEvento implements java.io.Serializable {
    private static final long serialVersionUID = -8032197800222994478L;
    private Integer id;

    private Tipoevento tipoEventos;

    private Evento eventos;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipoEventos_id", nullable = false)
    public Tipoevento getTipoEventos() {
        return tipoEventos;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eventos_id", nullable = false)
    public Evento getEventos() {
        return eventos;
    }

}