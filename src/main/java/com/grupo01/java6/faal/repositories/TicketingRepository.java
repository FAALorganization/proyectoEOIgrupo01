package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Ticketing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketingRepository extends JpaRepository<Ticketing, Integer> {
  // is it approved or not

  // Details of the ticket on a single query since  for the memoire
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
    WHERE (:aprobado IS NULL OR t.aprobado = :aprobado)
    
""")
  // Efficient only the approved one  Builds a database query with LIMIT and OFFSET (or equivalent SQL syntax) using the page size and number.
  Page<Ticketing> findAllByAprobado(Boolean aprobado, Pageable pageable);

  List<Ticketing> findByAprobado(Boolean aprobado);

  @Query("""
        SELECT t FROM Ticketing t
        WHERE (:aprobado IS NULL OR t.aprobado = :aprobado)
        AND (:search IS NULL OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :search, '%')))
        AND t.eliminacion IS NULL
        """)
  Page<Ticketing> findFilteredTickets(@Param("aprobado") Boolean aprobado,
                                      @Param("search") String search,
                                      Pageable pageable);

  Optional<Ticketing> findActiveById(Integer id);

  List<Ticketing> id(Integer id);
}