package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notificacion", indexes = {
        @Index(name = "idTipo", columnList = "idTipo"),
        @Index(name = "fk_notificacion_login1_idx", columnList = "idLogin")
})
public class Notificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotificacion", nullable = false)
    private Integer id;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fechaCreacion")
    private Instant fechaCreacion;

    @Column(name = "esLeida")
    private Boolean leida;

    @Column(name = "urlAccionSent")
    private String urlAccionSent;

    // Fecha y hora de asignación
    private LocalDateTime assignedDate;

    // Indica si la asignación está activa (por defecto es verdadero)
    private boolean isActive = true;

    // quien lo recibe
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Login recipient;


    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticketing ticket;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipo")
    private TipoNotificacion idTipoNotificacion;

}