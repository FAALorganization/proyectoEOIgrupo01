package com.grupo01.java6.faal.repositories;


import com.grupo01.java6.faal.dtos.NombreConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LoginRepository extends JpaRepository<Login, Integer> {


    @Query("select l from Login l left join fetch l.roles where l.emailPrimario = :email")
    Optional<Login> getLoginByEmailPrimario(String email);

    @Query("SELECT l.emailPrimario FROM Login l")
    Set<String> findAllEmails();

    @Query("""
    SELECT new com.grupo01.java6.faal.dtos.NombreDTO(d.nombre, d.apellidos)
    FROM Login l
    JOIN l.jefeLogin jefe
    JOIN jefe.subordinados sub
    JOIN Detallesdeusuario d ON d.usuarioLogin = sub
    WHERE l.emailPrimario = :email
""")
    List<NombreDTO> findCompanerosByEmail(@Param("email") String email);

    @Query("""
    SELECT new com.grupo01.java6.faal.dtos.NombreConAusenciasDTO(
        d.nombre,
        d.apellidos,
        a.fechaInicio,
        a.fechaFin
)
    FROM Login usuario
    JOIN Login jefe ON usuario.jefeLogin.id = jefe.id
    JOIN Login sub ON sub.jefeLogin.id = jefe.id
    JOIN Detallesdeusuario d ON d.usuarioLogin.id = sub.id
    JOIN Ausencias a ON a.loginAusencias.id = sub.id
    WHERE usuario.emailPrimario = :email
      AND a.tiposAusencias.id = 1
      AND a.aprobado = true
""")
    List<NombreConAusenciasDTO> obtenerCompanerosConAusencias(@Param("email") String email);

}
