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
@Entity(name = "Prioridade")
@Table(name = "prioridades", schema = "faal")
public class Prioridade implements Serializable{
    @Id
    @Column(name = "idPri", nullable = false)
    private Integer id;


    @Column(name = "prioridades_enum", nullable = false, length = 100)
    private String prioridadesEnum;

}