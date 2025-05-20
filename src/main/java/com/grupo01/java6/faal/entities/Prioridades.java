package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Prioridade")
@Table(name = "prioridades", schema = "faal")
public class Prioridades implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPri", nullable = false)
    private Integer id;
    @Column(name = "prioridades_enum", nullable = false, length = 100)
    private String prioridadesEnum;

}