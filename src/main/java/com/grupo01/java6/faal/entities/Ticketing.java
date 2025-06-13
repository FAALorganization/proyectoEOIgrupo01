package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Ticketing")
@Table(name = "ticketing", indexes = {
        @Index(name = "id_prior", columnList = "id_prior")
})
public class Ticketing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "modificacion")
    private LocalDate modificacion;

    @Column(name = "eliminacion")
    private LocalDate eliminacion;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "tipo_ticket", length = 45)
    private String tipoTicket;

    @Column(name = "asunto", length = 50)
    private String asunto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prior")
    private Prioridades idPrior;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_aprobador")
    private Login usuarioAprobador;

    // NEW: Add creator field
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_creador")
    private Login usuarioCreador;

    // Audit fields
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper method for setting creator
    public void setCreatedBy(Login usuario) {
        this.usuarioCreador = usuario;
    }
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (fechaInicio == null) {
            fechaInicio = LocalDate.now();
        }
        if (aprobado == null) {
            aprobado = false;
        }
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        modificacion = LocalDate.now();
    }
}