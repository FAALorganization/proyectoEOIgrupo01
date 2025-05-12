package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Login")
@Table(name = "login", schema = "faal", indexes = {
        @Index(name = "fk_login_detallesdeusuario1_idx", columnList = "idUsuario")
})
public class Login implements java.io.Serializable {
    private static final long serialVersionUID = 3442371899906328451L;
    private Integer id;

    private String emailPrimario;

    private String contrasena;

    private String token;

    private String imgAvatar;

    private Instant lastLoginDay;

    private Detallesdeusuario idUsuario;

    @Id
    @Column(name = "idLogin", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "emailPrimario", length = 250)
    public String getEmailPrimario() {
        return emailPrimario;
    }

    @Column(name = "contrasena")
    public String getContrasena() {
        return contrasena;
    }

    @Column(name = "token", length = 50)
    public String getToken() {
        return token;
    }

    @Column(name = "img_avatar", length = 50)
    public String getImgAvatar() {
        return imgAvatar;
    }

    @Column(name = "lastLoginDay")
    public Instant getLastLoginDay() {
        return lastLoginDay;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    public Detallesdeusuario getIdUsuario() {
        return idUsuario;
    }

}