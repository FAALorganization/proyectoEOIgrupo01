package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notificacion", schema = "faal", indexes = {
        @Index(name = "idTipo", columnList = "idTipo"),
        @Index(name = "fk_notificacion_login1_idx", columnList = "idLogin")
})
public class Notificacion implements java.io.Serializable {
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginNotif;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idTipo")
    private TipoNotificacion idTipo;

}