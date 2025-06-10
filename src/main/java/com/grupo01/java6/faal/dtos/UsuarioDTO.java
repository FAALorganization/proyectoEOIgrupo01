package com.grupo01.java6.faal.dtos;

public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;

    public UsuarioDTO(Integer id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}