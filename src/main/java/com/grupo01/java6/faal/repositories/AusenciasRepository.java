package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Ausencias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AusenciasRepository extends JpaRepository<Ausencias, Integer> {
    List<Ausencias> findByLoginAusencias_Id(Integer idLogin);
    Optional<Ausencias> findByFechaFinAndFechaInicio(LocalDate fechaFin, LocalDate fechaInicio);
}
