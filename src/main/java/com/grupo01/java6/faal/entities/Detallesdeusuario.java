package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "detallesdeusuario", schema = "faal")
public class Detallesdeusuario implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "poblacion")
    private String poblacion;
    @Column(name = "email_personal")
    private String emailPersonal;
    @Column(name = "tlf")
    private String tlf;
    @Column(name = "tlf2")
    private String tlf2;
    @Column(name = "codigo_postal")
    private Integer codigoPostal;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "contacto_emergencia")
    private String contactoEmergencia;
    @Column(name = "pais")
    private String pais;
    @Column(name = "token_img")
    private String tokenImg;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Login loginUsuario;

    @OneToMany(mappedBy = "loginJefe", fetch = FetchType.LAZY)
    private List<Login> subordinados;
}