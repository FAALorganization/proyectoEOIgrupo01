package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesDeUsuarioRepository extends JpaRepository<Detallesdeusuario, Integer> {
    Detallesdeusuario findByNombreAndApellidos(String nombre, String apellidos);
}
