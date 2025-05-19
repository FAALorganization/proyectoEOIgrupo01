package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chat", schema = "faal", indexes = {
        @Index(name = "fk_chat_login1_idx", columnList = "idRemitente"),
        @Index(name = "fk_chat_login2_idx", columnList = "idDestinatario")
})
public class Chat implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Integer id;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha_envio")
    private Instant fechaEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_login_remitente", nullable = false)
    private Login remitente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_login_destinatario", nullable = false)
    private Login destinatario;

}