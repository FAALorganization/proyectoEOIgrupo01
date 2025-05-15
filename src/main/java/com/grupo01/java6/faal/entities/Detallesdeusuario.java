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
@Entity(name = "Detallesdeusuario")
@Table(name = "detallesdeusuario", schema = "faal")
public class Detallesdeusuario implements Serializable {
    private static final long serialVersionUID = -8232431230616355220L;

    @Id
    @Column(name = "idUsuario", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "apellidos", length = 35)
    private String apellidos;

    @Column(name = "poblacion", length = 25)
    private String poblacion;

    @Column(name = "emailPersonal", length = 250)
    private String emailPersonal;

    @Column(name = "tlf", length = 25)
    private String tlf;

    @Column(name = "tlf2", length = 25)
    private String tlf2;

    @Column(name = "codigoPostal", nullable = false)
    private Integer codigoPostal;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "contactoEmergencia", length = 25)
    private String contactoEmergencia;

    @Column(name = "pais", length = 30)
    private String pais;

    @Column(name = "token_img", length = 50)
    private String tokenImg;

}