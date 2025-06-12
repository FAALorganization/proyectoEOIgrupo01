package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    @Query("SELECT DISTINCT p FROM Proyecto p LEFT JOIN FETCH p.documentos")
    List<Proyecto> findAllConDocumentos();

    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM Proyecto p JOIN p.usuarios u WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND u = :usuario")
    List<Proyecto> findByNombreContainingIgnoreCaseAndUsuarios(@Param("nombre") String nombre, @Param("usuario") Login usuario);


}
