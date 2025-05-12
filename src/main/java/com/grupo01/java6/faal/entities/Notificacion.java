package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Notificacion")
@Table(name = "notificacion", schema = "faal", indexes = {
        @Index(name = "idTipo", columnList = "idTipo"),
        @Index(name = "fk_notificacion_login1_idx", columnList = "idLogin")
})
public class Notificacion implements java.io.Serializable {
    private static final long serialVersionUID = -6531761031593887725L;
    private Integer id;

    private com.grupo01.java6.faal.entities.TipoNotificacion idTipo;

    private String mensaje;

    private Instant fechaCreacion;

    private Byte leida;

    private String urlAccionSent;

    private Login idLogin;

    @Id
    @Column(name = "idNotificacion", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idTipo")
    public com.grupo01.java6.faal.entities.TipoNotificacion getIdTipo() {
        return idTipo;
    }

    @Lob
    @Column(name = "mensaje", nullable = false)
    public String getMensaje() {
        return mensaje;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaCreacion")
    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    @ColumnDefault("0")
    @Column(name = "leida")
    public Byte getLeida() {
        return leida;
    }

    @Column(name = "urlAccionSent")
    public String getUrlAccionSent() {
        return urlAccionSent;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public Login getIdLogin() {
        return idLogin;
    }

}