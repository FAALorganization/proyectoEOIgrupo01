package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mensaje_leido", indexes = {
        @Index(name = "fk_mensaje_leido_login1_idx", columnList = "idLogin")
})
public class MensajeLeido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMensaje", nullable = false)
    private Integer id;

    @Column(name = "fechaLeido")
    private LocalDateTime fechaLeido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idMensajeGrupo")
    private MensajeGrupo mensajeGrupo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idMensajeEnviado")
    private Mensaje mensaje;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    private Login loginMensajeLeido;

}