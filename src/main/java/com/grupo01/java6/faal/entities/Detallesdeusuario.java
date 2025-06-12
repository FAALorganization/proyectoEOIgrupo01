package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Detallesdeusuario")
@Table(name = "detallesdeusuario")
public class Detallesdeusuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 35)
    private String apellidos;

    @Column(name = "poblacion", length = 25)
    private String poblacion;

    @Column(name = "emailPersonal", length = 250)
    private String emailPersonal;

    @Column(name = "tlf", length = 25)
    private String tlf;

    @Column(name = "tlf2", length = 25)
    private String tlf2;

    @Column(name = "codigoPostal")
    private Integer codigoPostal;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "contactoEmergencia", length = 25)
    private String contactoEmergencia;

    @Column(name = "pais", length = 30)
    private String pais;

    //He cambiado tambien esta parte para que mapee directamente con Login y no haya confusiones con jefe.
    @OneToOne(mappedBy = "idDetallesDeUsuario", fetch = FetchType.LAZY)
    private Login usuarioLogin;
}