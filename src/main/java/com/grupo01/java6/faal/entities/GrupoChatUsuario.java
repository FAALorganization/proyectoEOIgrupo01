package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grupo_chat_usuario", schema = "faal", indexes = {
        @Index(name = "fk_grupo_chat_usuario_login1_idx", columnList = "idLogin")
})
public class GrupoChatUsuario implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo", nullable = false)
    private Integer id;
    @Column(name = "fecha_ingreso")
    private Instant fechaIngreso;
    @Column(name = "es_admin")
    private Boolean esAdmin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_login", nullable = false)
    private Login login;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_grupo", nullable = false)
    private GrupoChat grupoChat;


}