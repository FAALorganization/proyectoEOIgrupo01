package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tiposausencias")
public class TiposAusencias implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 45)
    private String descripcion;

}