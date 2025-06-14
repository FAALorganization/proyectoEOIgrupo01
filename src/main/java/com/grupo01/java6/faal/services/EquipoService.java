package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.Equipo;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private LoginService loginService;

    @Transactional
    public List<Equipo> obtenerTodosLosEquipos() {
        List<Equipo> equipos = equipoRepository.findAll();
        equipos.forEach(equipo -> equipo.getListaLogin().forEach(login -> {
            if (login.getIdDetallesDeUsuario() != null) {
                Hibernate.initialize(login.getIdDetallesDeUsuario());
            }
        }));
        return equipos != null ? equipos : List.of(); // Devuelve lista vacía si es nulo
    }
    @Transactional
    public Map<Integer, List<CompaneroDTO>> obtenerNombresPorEquipo() {
        List<Equipo> equipos = equipoRepository.findAll();

        Map<Integer, List<CompaneroDTO>> nombresPorEquipo = new HashMap<>();

        for (Equipo equipo : equipos) {
            List<CompaneroDTO> nombres = equipo.getListaLogin().stream()
                    .map(login -> {
                        var user = login.getIdDetallesDeUsuario();
                        return new CompaneroDTO(login.getId(), user.getNombre(), user.getApellidos()); // Incluir loginId
                    })
                    .toList();

            nombresPorEquipo.put(equipo.getId(), nombres);
        }

        return nombresPorEquipo;
    }


}
