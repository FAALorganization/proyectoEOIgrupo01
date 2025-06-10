package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    // Puedes agregar métodos personalizados aquí si lo necesitas más adelante
}
