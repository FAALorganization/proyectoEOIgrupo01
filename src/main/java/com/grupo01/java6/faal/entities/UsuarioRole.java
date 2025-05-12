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
@Entity(name = "Usuario_Role")
@Table(name = "usuario_roles", schema = "faal", indexes = {
        @Index(name = "idRol", columnList = "idRol"),
        @Index(name = "fk_usuario_roles_login1_idx", columnList = "idLogin")
})
public class UsuarioRole implements java.io.Serializable {
    private static final long serialVersionUID = -4024495305340252965L;
    private Integer id;

    private Role idRol;

    private Login idLogin;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idRol", nullable = false)
    public Role getIdRol() {
        return idRol;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLogin", nullable = false)
    public Login getIdLogin() {
        return idLogin;
    }

}