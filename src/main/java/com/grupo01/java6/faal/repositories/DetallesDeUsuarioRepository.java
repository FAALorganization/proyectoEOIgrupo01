package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DetallesDeUsuarioRepository extends JpaRepository<Detallesdeusuario, Integer> {
    Detallesdeusuario findByNombreAndApellidos(String nombre, String apellidos);

    @Query("SELECT r.descripcion FROM Roles r JOIN r.loginRol l ON l.id = r.loginRol.id JOIN Detallesdeusuario d ON l.id = d.id WHERE d.id = :idUsuario")
    String findRolByUsuarioId(Integer idUsuario);

    @Query("SELECT d FROM Detallesdeusuario d WHERE d.usuarioLogin.activo = true")
    List<Detallesdeusuario> findUsuariosActivos();
}