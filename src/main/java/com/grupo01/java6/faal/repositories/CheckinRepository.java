package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {
    // Aquí puedes poner métodos personalizados si quieres
}