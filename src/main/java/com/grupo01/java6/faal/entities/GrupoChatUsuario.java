package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;

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
    private LocalDate fechaIngreso;

    @Column(name = "es_admin")
    private Boolean esAdmin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginGrupoChatUsuarios;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGrupoChatUsuario", nullable = false)
    private GrupoChat grupoChatUsuario;

}