package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Tipo_Notificacion")
@Table(name = "tipo_notificacion", schema = "faal")
public class TipoNotificacion implements java.io.Serializable {
    private static final long serialVersionUID = 9194583128968915166L;
    private Integer id;

    private String descripcion;

    @Id
    @Column(name = "idTipo", nullable = false)
    public Integer getId() {
        return id;
    }

    @Lob
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

}