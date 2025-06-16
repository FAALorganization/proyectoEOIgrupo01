package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Prioridade")
@Table(name = "prioridades")
public class Prioridades implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPri", nullable = false)
    private Integer id;
// if created an enum
    //@Column(name = "prioridades_enum", nullable = false, length = 100)
    //private String prioridadesEnum;

    @Column(nullable = false, length = 20)
    private String value;  // Should store "high", "medium", "low"

    @Column(nullable = false, length = 20)
    private String displayName; // Should store "Alta", "Media", "Baja"
    // Business key equality
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prioridades)) return false;
        return value != null && value.equals(((Prioridades) o).getValue());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();}
}