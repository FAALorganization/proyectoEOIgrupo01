package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Post")
@Table(name = "post", schema = "faal", indexes = {
        @Index(name = "fk_post_foro1_idx", columnList = "foro_idForo"),
        @Index(name = "fk_post_login1_idx", columnList = "idLogin")
})
public class Post implements java.io.Serializable {
    private static final long serialVersionUID = 7779203180431873908L;
    private Integer id;

    private Foro foroIdforo;

    private String mensaje;

    private Instant fechaEnvio;

    private Byte editado;

    private Instant fechaEdicion;

    private Login idLogin;

    @Id
    @Column(name = "idPost", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "foro_idForo", nullable = false)
    public Foro getForoIdforo() {
        return foroIdforo;
    }

    @Lob
    @Column(name = "mensaje", nullable = false)
    public String getMensaje() {
        return mensaje;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaEnvio")
    public Instant getFechaEnvio() {
        return fechaEnvio;
    }

    @ColumnDefault("0")
    @Column(name = "editado")
    public Byte getEditado() {
        return editado;
    }

    @Column(name = "fechaEdicion")
    public Instant getFechaEdicion() {
        return fechaEdicion;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public Login getIdLogin() {
        return idLogin;
    }

}