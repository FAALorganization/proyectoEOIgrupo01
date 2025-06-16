package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Ticketing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketingRepository extends JpaRepository<Ticketing, Integer> {

  /**
   * Find a single ticket by ID only if it is not marked as deleted.
   */
  @Query("SELECT t FROM Ticketing t WHERE t.id = :id AND t.eliminacion IS NULL")
  Optional<Ticketing> findActiveById(Integer id);

  /**
   * Find all tickets not marked as deleted.
   */
  @Query("SELECT t FROM Ticketing t WHERE t.eliminacion IS NULL")
  List<Ticketing> findAllActive();

  /**
   * Find tickets created by a specific user (by primary email), excluding deleted.
   */
  @Query("SELECT t FROM Ticketing t WHERE t.usuarioCreador.emailPrimario = :email AND t.eliminacion IS NULL")
  List<Ticketing> findByCreatorEmail(String email);

  /**
   * Paginated query to find tickets by approval status, excluding deleted.
   */
  @Query("SELECT t FROM Ticketing t WHERE t.aprobado = :aprobado AND t.eliminacion IS NULL")
  Page<Ticketing> findByAprobadoAndEliminacionIsNull(Boolean aprobado, Pageable pageable);
}
