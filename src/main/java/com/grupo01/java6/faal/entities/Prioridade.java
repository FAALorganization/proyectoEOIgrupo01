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
@Entity(name = "Prioridade")
@Table(name = "prioridades", schema = "faal")
public class Prioridade implements java.io.Serializable {
    private static final long serialVersionUID = 9148831855679094756L;
    private Integer id;

    private String prioridadesEnum;

    @Id
    @Column(name = "idPri", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "prioridades_enum", nullable = false, length = 100)
    public String getPrioridadesEnum() {
        return prioridadesEnum;
    }

}