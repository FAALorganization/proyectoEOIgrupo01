package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.AsignacionProyectoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionProyectoUsuarioRepository extends JpaRepository<AsignacionProyectoUsuario, Integer> {

    List<AsignacionProyectoUsuario> findByUsuario_Id(Integer usuarioId);

    boolean existsByProyecto_IdAndUsuario_Id(Integer proyectoId, Integer usuarioId);

}