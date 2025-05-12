package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity(name = "Login")
@Table(name = "login", schema = "faal", indexes = {
        @Index(name = "fk_login_detallesdeusuario1_idx", columnList = "idUsuario")
})

public class Login implements Serializable {
    @Id
    @Column(name = "idLogin", nullable = false)
    private Integer id;

    @Column(name = "emailPrimario", length = 250)
    private String emailPrimario;
    @Column(name = "password")
    private String password;
    @Column(name = "token", length = 50)
    private String token;
    @Column(name = "img_avatar", length = 50)
    private String imgAvatar;
    @Column(name = "lastLoginDay")
    private Instant lastLoginDay;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Detallesdeusuario idUsuario;


}