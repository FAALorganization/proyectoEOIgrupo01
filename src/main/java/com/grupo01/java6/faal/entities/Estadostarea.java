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
@Entity(name = "Estadostarea")
@Table(name = "estadostareas", schema = "faal")
public class Estadostarea implements java.io.Serializable {
    private static final long serialVersionUID = -796646329513975523L;
    private Integer id;

    private String descripcion;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "descripcion", length = 45)
    public String getDescripcion() {
        return descripcion;
    }

}