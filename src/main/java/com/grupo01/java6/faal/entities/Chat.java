package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Chat")
@Table(name = "chat", indexes = {
        @Index(name = "fk_chat_login1_idx", columnList = "idRemitente"),
        @Index(name = "fk_chat_login2_idx", columnList = "idDestinatario")
})
public class Chat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChat", nullable = false)
    private Integer id;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaEnvio")
    private LocalDate fechaEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRemitente", nullable = false)
    private Login idRemitente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDestinatario", nullable = false)
    private Login idDestinatario;

}