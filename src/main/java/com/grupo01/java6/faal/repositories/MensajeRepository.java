package com.grupo01.java6.faal.repositories;

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

}