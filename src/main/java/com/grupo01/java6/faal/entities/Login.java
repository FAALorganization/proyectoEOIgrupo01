package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "login", schema = "faal", indexes = {
        @Index(name = "fk_login_detallesdeusuario1_idx", columnList = "idUsuario")
})

public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login", nullable = false)
    private Integer id;

    @Column(name = "email_primario", length = 250)
    private String emailPrimario;

    @Column(name = "password")
    private String password;

    @Column(name = "token", length = 50)
    private String token;

    @Column(name = "img_avatar", length = 50)
    private String imgAvatar;

    @Column(name = "last_login_day")
    private LocalDate lastLoginDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_jefe", nullable = false, unique = true)
    private Login jefeLogin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true)
    private Detallesdeusuario idDetallesDeUsuario;

    @ManyToMany( mappedBy = "listaLogin")
    private Set<Equipo> listaEquipos;


}