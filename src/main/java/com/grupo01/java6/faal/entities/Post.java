package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
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
public class Post implements Serializable {

    @Id
    @Column(name = "idPost", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "foro_idForo", nullable = false)
    private Foro idForo;

    @Lob
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaEnvio")
    private Instant fechaEnvio;

    @ColumnDefault("0")
    @Column(name = "editado")
    private Byte editado;

    @Column(name = "fechaEdicion")
    private Instant fechaEdicion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login idLogin;

}