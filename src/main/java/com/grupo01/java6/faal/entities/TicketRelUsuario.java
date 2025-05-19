package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket_rel_usuario", schema = "faal", indexes = {
        @Index(name = "id_ticketing", columnList = "id_ticketing"),
        @Index(name = "fk_ticket_rel_usuario_login1_idx", columnList = "login_idLogin")
})
public class TicketRelUsuario implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fechaAsignacion")
    private LocalDate fechaAsignacion;
    @Column(name = "estadoTicket")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_ticketing")
    private Ticketing ticket;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login login;

}