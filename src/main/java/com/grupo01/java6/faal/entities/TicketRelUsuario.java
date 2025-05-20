package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Ticket_Rel_Usuario")
@Table(name = "ticket_rel_usuario", schema = "faal", indexes = {
        @Index(name = "id_ticketing", columnList = "id_ticketing"),
        @Index(name = "fk_ticket_rel_usuario_login1_idx", columnList = "login_idLogin")
})
public class TicketRelUsuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_ticketing")
    private Ticketing idTicketing;
    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;
    @Column(name = "estado")
    private Boolean estado;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_idLogin", nullable = false)
    private Login loginIdlogin;

}