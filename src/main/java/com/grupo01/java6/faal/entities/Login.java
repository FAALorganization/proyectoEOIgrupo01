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
@Table(name = "login", indexes = {
        @Index(name = "fk_login_detallesdeusuario1_idx", columnList = "idUsuario")
})
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login", nullable = false)
    private Integer id;

    @Column(name = "email_primario", length = 250)
    private String emailPrimario;

    @Column(name = "password")  // Removed @ElementCollection
    private String password;

    public String getContrasena() {
        return password;
    }

    @Column(name = "token", length = 50)
    private String token;

    @Column(name = "img_avatar", length = 50)
    private String imgAvatar;

    @Column(name = "last_login_day")
    private LocalDate lastLoginDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_jefe")
    private Login jefeLogin;

    @OneToMany(mappedBy = "jefeLogin")
    private Set<Login> subordinados;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true)
    private Detallesdeusuario idDetallesDeUsuario;

    @ManyToMany(mappedBy = "listaLogin")
    private Set<Equipo> listaEquipos;

    @OneToMany(mappedBy = "loginRol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Roles> roles;

    public Set<Roles> getRoles() {
        return roles;
    }
}