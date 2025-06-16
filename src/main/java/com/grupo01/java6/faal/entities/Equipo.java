package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Equipo")
@Table(name = "equipo", indexes = {
        @Index(name = "fk_equipo_grupo_chat1_idx", columnList = "idGrupoChat")
})
public class Equipo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEquipo", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "idGrupoChat", nullable = true)
    private GrupoChat idGrupoChat;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Login> listaLogin;

    @Transient
    private Set<Integer> listaLoginIds;  // este es solo para el form, no va a BD

}