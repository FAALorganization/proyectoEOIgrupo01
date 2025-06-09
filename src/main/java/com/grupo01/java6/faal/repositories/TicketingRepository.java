package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Ticketing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketingRepository extends JpaRepository<Ticketing, Integer> {
  // is it approved or not
  List<Ticketing> findByAprobado(Boolean aprobado);
}
