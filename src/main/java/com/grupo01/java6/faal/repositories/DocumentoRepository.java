package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByProyecto_Id(Long idProyecto);
}