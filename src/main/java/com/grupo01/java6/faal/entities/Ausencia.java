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
@Entity(name = "Ausencia")
@Table(name = "ausencias", schema = "faal", indexes = {
        @Index(name = "fk_ausencias_login1_idx", columnList = "idLogin"),
        @Index(name = "fk_ausencias_tiposAusencias1_idx", columnList = "tiposAusencias_id")
})
public class Ausencia implements java.io.Serializable {
    private static final long serialVersionUID = -8182403092896352308L;
    private Integer id;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String justificacion;

    private String documentos;

    private Integer calcularDias;

    private Byte aprobado;

    private com.grupo01.java6.faal.entities.Login idLogin;

    private com.grupo01.java6.faal.entities.Tiposausencia tiposAusencias;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "fechaInicio")
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    @Column(name = "fechaFin")
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Lob
    @Column(name = "justificacion")
    public String getJustificacion() {
        return justificacion;
    }

    @Column(name = "documentos", length = 250)
    public String getDocumentos() {
        return documentos;
    }

    @Column(name = "calcularDias")
    public Integer getCalcularDias() {
        return calcularDias;
    }

    @Column(name = "aprobado")
    public Byte getAprobado() {
        return aprobado;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public com.grupo01.java6.faal.entities.Login getIdLogin() {
        return idLogin;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tiposAusencias_id", nullable = false)
    public com.grupo01.java6.faal.entities.Tiposausencia getTiposAusencias() {
        return tiposAusencias;
    }

}