package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Ticketing")
@Table(name = "ticketing", schema = "faal", indexes = {
        @Index(name = "id_prior", columnList = "id_prior")
})
public class Ticketing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prior")
    private Prioridades idPrior;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre", length = 45)
    private String nombre;
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    @Column(name = "fechaFin")
    private LocalDate fechaFin;
    @Column(name = "modificacion")
    private LocalDate modificacion;
    @Column(name = "eliminacion")
    private LocalDate eliminacion;

    @Column(name = "aprobado")
    private Boolean aprobado;
    @Column(name = "tipoTicket", length = 45)
    private String tipoTicket;
    @Column(name = "asunto", length = 50)
    private String asunto;

}