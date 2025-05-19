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
@Table(name = "ticketing", schema = "faal", indexes = {
        @Index(name = "id_prior", columnList = "id_prior")
})
public class Ticketing implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    @Column(name = "fechaFin")
    private LocalDate fechaFin;
    @Column(name = "fechaModificacion")
    private LocalDate modificacion;
    @Column(name = "fechaEliminacion")
    private LocalDate eliminacion;
    @Column(name = "esAprobado")
    private Boolean aprobado;
    @Column(name = "tipoTicket", length = 45)
    private String tipoTicket;
    @Column(name = "asunto", length = 50)
    private String asunto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prior")
    private Prioridades prioridad;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<TicketRelUsuario> ticketRelsUsario;

}