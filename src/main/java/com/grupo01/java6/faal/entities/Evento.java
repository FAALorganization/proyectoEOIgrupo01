package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "eventos", schema = "faal", indexes = {
        @Index(name = "fk_eventos_login1_idx", columnList = "idLogin")
})
public class Evento implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "titulo", length = 45)
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    @Column(name = "fecha_eliminada")
    private LocalDate fechaEliminada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_login", nullable = false)
    private Login login;

    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY)
    private List<TiposRelEvento> tiposEventos;

}