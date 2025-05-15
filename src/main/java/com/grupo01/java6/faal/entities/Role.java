package com.grupo01.java6.faal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Role")
@Table(name = "roles", schema = "faal")
public class Role implements Serializable {
    @Id
    @Column(name = "idRol", nullable = false)
    private Integer id;
    @Column(name = "descripcion", length = 10)
    private String descripcion;


}