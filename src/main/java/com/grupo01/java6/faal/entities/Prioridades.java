package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "prioridades", schema = "faal")
public class Prioridades implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pri", nullable = false)
    private Integer id;
    @Column(name = "prioridades_enum", nullable = false, length = 100)
    private String prioridadesEnum;

    @OneToMany(mappedBy = "prioridad", fetch = FetchType.LAZY)
    private List<Ticketing> tickets;

    @OneToMany(mappedBy = "prioridad", fetch = FetchType.LAZY)
    private List<Sla> slas;
}