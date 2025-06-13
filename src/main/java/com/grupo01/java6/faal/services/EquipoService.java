package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Equipo;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private LoginService loginService;

    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoRepository.findAll();
    }

    public Equipo guardarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo obtenerEquipoPorId(Integer id) {
        Optional<Equipo> equipo = equipoRepository.findById(id);
        return equipo.orElse(null);
    }

    public void eliminarEquipo(Integer id) {
        equipoRepository.deleteById(id);
    }

    public Integer obtenerIdJefeActual() {
        // Obtiene el email del jefe autenticado
        String emailJefe = SecurityContextHolder.getContext().getAuthentication().getName();
        // Busca el jefe en la base de datos
        Login jefeActual = loginService.getUserByEmail(emailJefe);
        // Verifica si jefeActual no es null antes de llamar a getId()
        return jefeActual != null ? jefeActual.getId() : null;
    }
}