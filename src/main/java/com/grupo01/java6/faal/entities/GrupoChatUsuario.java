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
@Entity(name = "Grupo_Chat_Usuario")
@Table(name = "grupo_chat_usuario", schema = "faal", indexes = {
        @Index(name = "fk_grupo_chat_usuario_login1_idx", columnList = "idLogin")
})
public class GrupoChatUsuario implements java.io.Serializable {
    private static final long serialVersionUID = 8784472570197998107L;
    private Integer id;

    private GrupoChat grupoChat;

    private Instant fechaIngreso;

    private Byte esAdmin;

    private com.grupo01.java6.faal.entities.Login idLogin;

    @Id
    @Column(name = "idGrupo", nullable = false)
    public Integer getId() {
        return id;
    }

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idGrupo", nullable = false)
    public GrupoChat getGrupoChat() {
        return grupoChat;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaIngreso")
    public Instant getFechaIngreso() {
        return fechaIngreso;
    }

    @ColumnDefault("0")
    @Column(name = "esAdmin")
    public Byte getEsAdmin() {
        return esAdmin;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdLogin() {
        return idLogin;
    }

}