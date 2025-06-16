package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.TiposAusencias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiposAusenciasRepository extends JpaRepository<TiposAusencias, Integer> {
    TiposAusencias getTiposAusenciasById(Integer id);
}
