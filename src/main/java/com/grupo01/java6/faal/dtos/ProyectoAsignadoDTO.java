package com.grupo01.java6.faal.dtos;

public class ProyectoAsignadoDTO {
    private Integer idProyecto;
    private String nombreProyecto;
    private String descripcionProyecto;

    public ProyectoAsignadoDTO(Integer idProyecto, String nombreProyecto, String descripcionProyecto) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }
}
