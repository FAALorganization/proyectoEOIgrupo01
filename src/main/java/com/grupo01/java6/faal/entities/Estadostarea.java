package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "estadostareas", schema = "faal")
public class Estadostarea implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "estadosTareas", fetch = FetchType.LAZY)
    private List<EstadosRelTarea> tareasRelacionadas;

}