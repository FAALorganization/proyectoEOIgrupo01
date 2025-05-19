package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Tipo_Notificacion")
@Table(name = "tipo_notificacion", schema = "faal")
public class TipoNotificacion implements Serializable{
    @Id
    @Column(name = "idTipo", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

}