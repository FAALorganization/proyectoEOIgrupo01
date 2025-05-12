package com.grupo01.java6.faal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Role")
@Table(name = "roles", schema = "faal")
public class Role implements java.io.Serializable {
    private static final long serialVersionUID = -6519962748012982772L;
    private Integer id;

    private String descripcion;

    @Id
    @Column(name = "idRol", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "descripcion", length = 10)
    public String getDescripcion() {
        return descripcion;
    }

}