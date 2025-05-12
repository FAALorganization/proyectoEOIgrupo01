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
@Entity(name = "Mensaje_Grupo")
@Table(name = "mensaje_grupo", schema = "faal", indexes = {
        @Index(name = "fk_mensaje_grupo_login1_idx", columnList = "idAutor"),
        @Index(name = "fk_mensaje_grupo_grupo_chat1_idx", columnList = "idGrupo")
})
public class MensajeGrupo implements java.io.Serializable {
    private static final long serialVersionUID = 9116604364605489792L;
    private Integer id;

    private String mensaje;

    private Instant fechaEnvio;

    private Login idAutor;

    private GrupoChat idGrupo;

    @Id
    @Column(name = "idMensaje", nullable = false)
    public Integer getId() {
        return id;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idAutor", nullable = false)
    public Login getIdAutor() {
        return idAutor;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGrupo", nullable = false)
    public GrupoChat getIdGrupo() {
        return idGrupo;
    }

}