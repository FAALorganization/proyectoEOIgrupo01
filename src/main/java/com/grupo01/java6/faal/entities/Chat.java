package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Chat")
@Table(name = "chat", schema = "faal", indexes = {
        @Index(name = "fk_chat_login1_idx", columnList = "idRemitente"),
        @Index(name = "fk_chat_login2_idx", columnList = "idDestinatario")
})
public class Chat implements java.io.Serializable {
    private static final long serialVersionUID = -7448095244108325050L;
    private Integer id;

    private String mensaje;

    private Instant fechaEnvio;

    private com.grupo01.java6.faal.entities.Login idRemitente;

    private com.grupo01.java6.faal.entities.Login idDestinatario;

    @Id
    @Column(name = "idChat", nullable = false)
    public Integer getId() {
        return id;
    }

    @Lob
    @Column(name = "mensaje", nullable = false)
    public String getMensaje() {
        return mensaje;
    }

    @ColumnDefault("current_timestamp()")
    @Column(name = "fechaEnvio")
    public Instant getFechaEnvio() {
        return fechaEnvio;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRemitente", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdRemitente() {
        return idRemitente;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDestinatario", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdDestinatario() {
        return idDestinatario;
    }

}