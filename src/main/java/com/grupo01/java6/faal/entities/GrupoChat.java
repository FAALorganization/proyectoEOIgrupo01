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
@Entity(name = "Grupo_Chat")
@Table(name = "grupo_chat", schema = "faal", indexes = {
        @Index(name = "fk_grupo_chat_login1_idx", columnList = "idLogin")
})
public class GrupoChat implements java.io.Serializable {
    private static final long serialVersionUID = -7499841177154410270L;
    private Integer id;

    private String nombre;

    private Byte esEquipo;

    private Instant fechaCreacion;

    private com.grupo01.java6.faal.entities.Login idLogin;

    @Id
    @Column(name = "idGrupo", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "nombre", nullable = false, length = 100)
    public String getNombre() {
        return nombre;
    }

    @ColumnDefault("1")
    @Column(name = "esEquipo")
    public Byte getEsEquipo() {
        return esEquipo;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaCreacion")
    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdLogin() {
        return idLogin;
    }

}