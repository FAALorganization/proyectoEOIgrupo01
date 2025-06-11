package com.grupo01.java6.faal.repositories;


import com.grupo01.java6.faal.entities.Prioridades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrioridadesRepository extends JpaRepository<Prioridades, Integer> {

    Optional<Prioridades> findById(Integer id);

    Optional<Object> findByPrioridadesEnum(String prioridad);
}