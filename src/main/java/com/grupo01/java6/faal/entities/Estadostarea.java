package com.grupo01.java6.faal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Estadostarea")
@Table(name = "estadostareas", schema = "faal")
public class Estadostarea implements Serializable {
    private static final long serialVersionUID = -796646329513975523L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "descripcion", length = 45)
    private String descripcion;

}