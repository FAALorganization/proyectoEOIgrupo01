package com.grupo01.java6.faal.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity(name = "Login_Equipo")
@Table(name = "login_equipo", schema = "faal", indexes = {
        @Index(name = "fk_usuario_equipo_login1_idx", columnList = "idLogin")
})
public class LoginEquipo implements java.io.Serializable {
    private static final long serialVersionUID = 8032556703655484036L;
    private Integer id;

    private Equipo equipo;

    private Login idLogin;

    @Id
    @Column(name = "idEquipo", nullable = false)
    public Integer getId() {
        return id;
    }

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idEquipo", nullable = false)
    public Equipo getEquipo() {
        return equipo;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public Login getIdLogin() {
        return idLogin;
    }

}