package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.management.Notification;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    private Boolean aprobado;

    private LocalDateTime fechaAprobacion;

    private String comentariosAprobacion;

    @Column(name = "tipo_ticket", length = 45)
    private String tipoTicket;

    @Column(name = "asunto", length = 50)
    private String asunto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prior")
    private Prioridades idPrior;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private SLATracking slaTracking;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketRelUsuario> assignedUsers = new ArrayList<>();

    /*@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();
*/
    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.OPEN;

    public enum TicketStatus {
        OPEN, IN_PROGRESS, PENDING_REVIEW, RESOLVED, CLOSED, REOPENED
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_aprobador")
    private Login usuarioAprobador;

    // creado por
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_creador")
    private Login usuarioCreador;

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

    // Adtional fieled not stored in bd
    @org.springframework.data.annotation.Transient
    private Integer telefono;
    @org.springframework.data.annotation.Transient // not stored in bd
    private Integer fechaQuejaDay;

    @org.springframework.data.annotation.Transient
    private String fechaQuejaMonth;

    @org.springframework.data.annotation.Transient
    private Integer fechaQuejaYear;

    @org.springframework.data.annotation.Transient
    private LocalDate fechaQueja ;
    private static final Map<String, Integer> SPANISH_MONTHS = Map.ofEntries(
            Map.entry("enero", 1),
            Map.entry("febrero", 2),
            Map.entry("marzo", 3),
            Map.entry("abril", 4),
            Map.entry("mayo", 5),
            Map.entry("junio", 6),
            Map.entry("julio", 7),
            Map.entry("agosto", 8),
            Map.entry("septiembre", 9),
            Map.entry("octubre", 10),
            Map.entry("noviembre", 11),
            Map.entry("diciembre", 12)
    );

    @Transient
    public LocalDate getFechaQueja() {
        if (fechaQuejaYear == null || fechaQuejaMonth == null || fechaQuejaDay == null) {
            return null;
        }

        try {
            // Try to parse month as enum first (English names)
            try {
                Month month = Month.valueOf(fechaQuejaMonth.toUpperCase());
                return LocalDate.of(fechaQuejaYear, month, fechaQuejaDay);
            } catch (IllegalArgumentException e) {
                // If English names fail, try Spanish month names
                String monthLower = fechaQuejaMonth.toLowerCase(Locale.ROOT);
                Integer monthNumber = SPANISH_MONTHS.get(monthLower);

                if (monthNumber != null) {
                    return LocalDate.of(fechaQuejaYear, monthNumber, fechaQuejaDay);
                }

                // If not found in Spanish map, try parsing as number
                try {
                    monthNumber = Integer.parseInt(fechaQuejaMonth);
                    return LocalDate.of(fechaQuejaYear, monthNumber, fechaQuejaDay);
                } catch (NumberFormatException nfe) {
                    return null;
                }
            }
        } catch (DateTimeException e) {
            return null;
        }
    }
    @org.springframework.data.annotation.Transient
    // transient bc m to lazy to modify the entiy so i will do it later  : )
    private String correoGerente;

    public void setPrioridad(Prioridades prioridad) {
    }


    // Fecha y hora de asignación
    private LocalDateTime assignedDate;

    // Indica si la asignación está activa (por defecto es verdadero)
    private boolean isActive = true;

    // Rol de la asignación (PRIMARY, SECONDARY, REVIEWER)
    @Enumerated(EnumType.STRING)
    private AssignmentRole role;

    // Enumeración de los posibles roles de asignación
    public enum AssignmentRole {
        PRIMARY,     // Principal
        SECONDARY,   // Secundario
        REVIEWER,    // Revisor
        APPROVER     // Aprobador
    }

}