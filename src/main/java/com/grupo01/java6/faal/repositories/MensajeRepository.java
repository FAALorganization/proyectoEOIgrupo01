package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.dtos.Mensaje2DTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    // Mensajes privados entre dos personas (id puede ser de emisor o receptor)
    List<Mensaje> findByEmisorAndReceptorOrderByFechaEnvioAsc(Login emisor, Login receptor);

    List<Mensaje> findByReceptorAndEmisorOrderByFechaEnvioAsc(Login receptor, Login emisor);

    // Mensajes grupales
    List<Mensaje> findByEsGrupalTrueOrderByFechaEnvioAsc();

    @Query("SELECT m FROM Mensaje m WHERE (m.emisor.id = :usuarioId OR m.receptor.id = :usuarioId) AND m.fechaEnvio >= :desde")
    List<Mensaje> findMensajesRecientes(@Param("usuarioId") Integer usuarioId, @Param("desde") LocalDateTime desde);

    @Query("""
    SELECT new com.grupo01.java6.faal.dtos.Mensaje2DTO(
        m.id, 
        m.emisor.id, 
        m.receptor.id, 
        m.contenido, 
        m.fechaEnvio, 
        m.esGrupal, 
        m.esLeido
    ) 
    FROM Mensaje m 
    WHERE (
        (m.emisor.id = :usuarioAId AND m.receptor.id = :usuarioBId) 
        OR 
        (m.emisor.id = :usuarioBId AND m.receptor.id = :usuarioAId)
    ) 
    AND m.fechaEnvio >= :desde 
    ORDER BY m.fechaEnvio ASC
    """)
    List<Mensaje2DTO> findMensajesPrivadosEntreUsuariosDesde(
            @Param("usuarioAId") Integer usuarioAId,
            @Param("usuarioBId") Integer usuarioBId,
            @Param("desde") LocalDateTime desde
    );



}