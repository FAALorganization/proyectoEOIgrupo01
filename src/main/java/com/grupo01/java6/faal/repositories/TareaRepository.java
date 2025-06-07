package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {
//    List<Tarea> findByFechaFinNullAndFechaEliminadaNull();
//    List<Tarea> findByFechaFinNotNull();
//    List<Tarea> findByFechaEliminadaNotNull();
//    List<Tarea> findByEstado(String estado);
//    List<Tarea> findByFechaLimiteIsNullOrFechaLimiteGreaterThan(LocalDate fechaActual);
//    List<Tarea> findByFechaLimiteLessThan(LocalDate fechaActual);
    List<Tarea> findByEstadoAndLoginTareaId(String estado, Integer loginId);
}