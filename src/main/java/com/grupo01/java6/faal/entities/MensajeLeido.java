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
@Entity(name = "Mensaje_Leido")
@Table(name = "mensaje_leido", schema = "faal", indexes = {
        @Index(name = "fk_mensaje_leido_login1_idx", columnList = "idLogin")
})
public class MensajeLeido implements java.io.Serializable {
    private static final long serialVersionUID = 4645539775479003013L;
    private Integer id;

    private MensajeGrupo mensajeGrupo;

    private Instant fechaLeido;

    private Login idLogin;

    @Id
    @Column(name = "idMensaje", nullable = false)
    public Integer getId() {
        return id;
    }

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idMensaje", nullable = false)
    public MensajeGrupo getMensajeGrupo() {
        return mensajeGrupo;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaLeido")
    public Instant getFechaLeido() {
        return fechaLeido;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public Login getIdLogin() {
        return idLogin;
    }

}