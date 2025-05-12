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
@Entity(name = "Detallesdeusuario")
@Table(name = "detallesdeusuario", schema = "faal")
public class Detallesdeusuario implements java.io.Serializable {
    private static final long serialVersionUID = -8232431230616355220L;
    private Integer id;

    private String nombre;

    private String apellidos;

    private String poblacion;

    private String emailPersonal;

    private String tlf;

    private String tlf2;

    private Integer codigoPostal;

    private String direccion;

    private String contactoEmergencia;

    private String pais;

    private String tokenImg;

    @Id
    @Column(name = "idUsuario", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "nombre", nullable = false, length = 20)
    public String getNombre() {
        return nombre;
    }

    @Column(name = "apellidos", length = 35)
    public String getApellidos() {
        return apellidos;
    }

    @Column(name = "poblacion", length = 25)
    public String getPoblacion() {
        return poblacion;
    }

    @Column(name = "emailPersonal", length = 250)
    public String getEmailPersonal() {
        return emailPersonal;
    }

    @Column(name = "tlf", length = 25)
    public String getTlf() {
        return tlf;
    }

    @Column(name = "tlf2", length = 25)
    public String getTlf2() {
        return tlf2;
    }

    @Column(name = "codigoPostal", nullable = false)
    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    @Column(name = "direccion", length = 150)
    public String getDireccion() {
        return direccion;
    }

    @Column(name = "contactoEmergencia", length = 25)
    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    @Column(name = "pais", length = 30)
    public String getPais() {
        return pais;
    }

    @Column(name = "token_img", length = 50)
    public String getTokenImg() {
        return tokenImg;
    }

}