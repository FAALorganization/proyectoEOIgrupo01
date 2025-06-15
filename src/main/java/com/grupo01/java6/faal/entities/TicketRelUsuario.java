package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Ticket_Rel_Usuario")
@Table(name = "ticket_rel_usuario", indexes = {
        @Index(name = "id_ticketing", columnList = "id_ticketing"),
        @Index(name = "fk_ticket_rel_usuario_login1_idx", columnList = "login_idLogin")
})
public class TicketRelUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

    @Column(name = "estado")
    private Boolean estado;

    private LocalDateTime assignedDate;
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    private AssignmentRole role;

    public enum AssignmentRole {
        PRIMARY,     // Principal
        SECONDARY,   // Secundario
        REVIEWER,    // Revisor
        APPROVER     // Aprobador
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticketing")
    private Ticketing ticket;  // Changed from idTicketing to ticket to match the mappedBy reference

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_idLogin", nullable = false)
    private Login loginIdlogin;
}