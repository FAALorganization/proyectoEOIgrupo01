package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Prioridades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrioridadesRepository extends JpaRepository<Prioridades, Integer> {

    Optional<Prioridades> findByValue(String value);

    @Query("SELECT p.value FROM Prioridade p")
    List<String> findAllPriorityValues();

    @Query("SELECT p.displayName FROM Prioridade p WHERE p.value = :value")
    Optional<String> findDisplayNameByValue(String value);

    boolean existsByValue(String value);
}