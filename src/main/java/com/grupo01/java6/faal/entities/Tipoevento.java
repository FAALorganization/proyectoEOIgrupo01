package com.grupo01.java6.faal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity(name = "Tipoevento")
@Table(name = "tipoeventos", schema = "faal")
public class Tipoevento implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 45)
    private String descripcion;
}