package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class TicketRelUsuario implements java.io.Serializable {
    private static final long serialVersionUID = -3289060759771381740L;
    private Integer id;

    private com.grupo01.java6.faal.entities.Ticketing idTicketing;

    private LocalDate fechaAsignacion;

    private Byte estado;

    private Login loginIdlogin;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_ticketing")
    public com.grupo01.java6.faal.entities.Ticketing getIdTicketing() {
        return idTicketing;
    }

    @Column(name = "fecha_asignacion")
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    @Column(name = "estado")
    public Byte getEstado() {
        return estado;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_idLogin", nullable = false)
    public Login getLoginIdlogin() {
        return loginIdlogin;
    }

}