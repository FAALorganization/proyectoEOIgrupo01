package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.TiposAusencias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposAusenciasRepository extends JpaRepository<TiposAusencias, Integer> {
    TiposAusencias getTiposAusenciasById(Integer id);
}
