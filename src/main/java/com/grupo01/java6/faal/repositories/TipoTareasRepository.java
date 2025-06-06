package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.TipoTareas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTareasRepository extends JpaRepository<TipoTareas, Integer> {
}
