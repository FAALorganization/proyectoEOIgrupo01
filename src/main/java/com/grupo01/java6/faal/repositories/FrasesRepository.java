package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Frases;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrasesRepository extends JpaRepository<Frases, Integer> {
    Optional<Frases> findById(Integer id);
}
