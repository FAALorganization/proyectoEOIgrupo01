package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Ticketing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketingRepository extends JpaRepository<Ticketing, Integer> {
  // is it approved or not

  // Details of the ticket on a singel query since i used fetch lazy it s more rapido for the memoire
  @Query("""
    SELECT new com.grupo01.java6.faal.dtos.TicketingDTO(
        t.id,
        t.nombre,
        t.asunto,
        t.descripcion,
        t.tipoTicket,
        t.fechaInicio,
        t.fechaFin,
        t.aprobado,
        p.prioridadesEnum
    )
    FROM Ticketing t
    JOIN t.idPrior p
""")
  // Efficient only the approved one  Builds a database query with LIMIT and OFFSET (or equivalent SQL syntax) using the page size and number.
  Page<TicketingDTO> findAllByAprobado(Boolean aprobado, Pageable pageable);
}
