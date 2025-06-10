package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}