package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Tipoevento")
@Table(name = "tipoeventos")
public class Tipoevento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "descripcion", length = 45)
    private String descripcion;
}