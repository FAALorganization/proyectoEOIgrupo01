package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

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
    private Instant lastLoginDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Detallesdeusuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jefe", nullable = false)
    private Detallesdeusuario loginJefe;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Ausencia> ausencias;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Checkin> checkins;

    @OneToMany(mappedBy = "remitente", fetch = FetchType.LAZY)
    private List<Chat> chatsEnviados;

    @OneToMany(mappedBy = "destinatario", fetch = FetchType.LAZY)
    private List<Chat> chatsRecibidos;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<GrupoChat> chatsGrupos;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Foro> foros;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Evento> eventos;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<GrupoChat> gruposChats;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<GrupoChatUsuario> gruposChatUsuarios;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<LoginEquipo> loginsEquipos;

    @OneToMany(mappedBy = "idAutor", fetch = FetchType.LAZY)
    private List<MensajeGrupo> mensajesGrupos;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<MensajeLeido> mensajesLeidos;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Notificacion> notificaciones;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<UsuarioRoles> usuarioRoles;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY)
    private List<TicketRelUsuario> ticketRelsUsario;


}