package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Ticketing")
@Table(name = "ticketing", schema = "faal", indexes = {
        @Index(name = "id_prior", columnList = "id_prior")
})
public class Ticketing implements java.io.Serializable {
    private static final long serialVersionUID = 4538406927474288757L;
    private Integer id;

    private Prioridade idPrior;

    private String descripcion;

    private String nombre;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDate modificacion;

    private LocalDate eliminacion;

    private Byte aprobado;

    private String tipoTicket;

    private String asunto;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prior")
    public Prioridade getIdPrior() {
        return idPrior;
    }

    @Lob
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    @Column(name = "nombre", length = 45)
    public String getNombre() {
        return nombre;
    }

    @Column(name = "fechaInicio")
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    @Column(name = "fechaFin")
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Column(name = "modificacion")
    public LocalDate getModificacion() {
        return modificacion;
    }

    @Column(name = "eliminacion")
    public LocalDate getEliminacion() {
        return eliminacion;
    }

    @Column(name = "aprobado")
    public Byte getAprobado() {
        return aprobado;
    }

    @Column(name = "tipoTicket", length = 45)
    public String getTipoTicket() {
        return tipoTicket;
    }

    @Column(name = "asunto", length = 50)
    public String getAsunto() {
        return asunto;
    }

}