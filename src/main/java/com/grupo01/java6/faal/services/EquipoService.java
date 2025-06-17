package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.Equipo;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return equipos != null ? equipos : List.of();
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
    @Transactional
    public void quitarMiembro(Integer equipoId, Integer loginId) {
        Optional<Equipo> equipoOptional = equipoRepository.findById(equipoId);
        if (equipoOptional.isPresent()) {
            Equipo equipo = equipoOptional.get();
            equipo.getListaLogin().removeIf(login -> login.getId().equals(loginId));
            equipoRepository.save(equipo);
        }
    }
    @Transactional
    public void editarEquipo(Integer equipoId, String nombre, String descripcion) {
        Logger logger = LoggerFactory.getLogger(EquipoService.class);

        logger.info("Iniciando edición del equipo con ID: {}", equipoId);
        logger.info("Nuevos valores - Nombre: {}, Descripción: {}", nombre, descripcion);

        Optional<Equipo> equipoOptional = equipoRepository.findById(equipoId);
        if (equipoOptional.isPresent()) {
            Equipo equipo = equipoOptional.get();
            logger.info("Equipo encontrado: {}", equipo);

            equipo.setNombre(nombre);
            equipo.setDescripcion(descripcion);
            equipoRepository.save(equipo);

            logger.info("Equipo actualizado: {}", equipo);
        } else {
            logger.warn("No se encontró un equipo con ID: {}", equipoId);
        }
    }
    @Transactional
    public List<CompaneroDTO> obtenerSubordinadosDelJefeActual() {
        // Obtener el usuario actual desde el contexto de seguridad
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Login jefe = loginService.getUserByEmail(email); // Obtener el jefe actual

        if (jefe != null) {
            return jefe.getSubordinados().stream()
                    .filter(subordinado -> !subordinado.getId().equals(jefe.getId())) // Excluir al propio jefe
                    .map(subordinado -> new CompaneroDTO(
                            subordinado.getId(),
                            subordinado.getIdDetallesDeUsuario().getNombre(),
                            subordinado.getIdDetallesDeUsuario().getApellidos()
                    ))
                    .toList();
        }
        return List.of();
    }
    @Transactional
    public void anadirMiembros(Integer equipoId, List<Integer> nuevosLoginIds) {
        Optional<Equipo> equipoOptional = equipoRepository.findById(equipoId);
        if (equipoOptional.isPresent()) {
            Equipo equipo = equipoOptional.get();
            List<Login> nuevosMiembros = nuevosLoginIds.stream()
                    .map(loginService::getUserById)
                    .filter(Objects::nonNull)
                    .toList();
            equipo.getListaLogin().addAll(nuevosMiembros);
            equipoRepository.save(equipo);
        }
    }
    @Transactional
    public void crearEquipo(String nombre, String descripcion, List<Integer> loginIds) {
        Equipo nuevoEquipo = new Equipo();
        nuevoEquipo.setNombre(nombre);
        nuevoEquipo.setDescripcion(descripcion);

        List<Login> miembros = loginIds.stream()
                .map(loginService::getUserById)
                .filter(Objects::nonNull)
                .toList();

        nuevoEquipo.setListaLogin(new HashSet<>(miembros));
        equipoRepository.save(nuevoEquipo);
    }
    @Transactional
    public void eliminarEquipo(Integer equipoId) {
        equipoRepository.deleteById(equipoId);
    }

}
