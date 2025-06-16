package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.AusenciaDTO;
import com.grupo01.java6.faal.dtos.EmpleadoConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.dtos.TareaDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.services.AusenciasService;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.RolesService;
import com.grupo01.java6.faal.services.TareaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class CalendarioController {


    private final LoginService loginService;
    private final TareaService tareaService;

    public CalendarioController(LoginService loginService, TareaService tareaService) {
        this.loginService = loginService;
        this.tareaService = tareaService;
    }


    @GetMapping("/calendario/tareas")
    @ResponseBody
    public List<TareaDTO> obtenerTareasUsuario(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUser = auth.getName();
        Login loginUser = loginService.obtainUser(correoUser);

        Integer idUser = loginUser.getId();
        List<Tarea> tareasPendientes = tareaService.obtenerTareasEstadoAndUsuario(idUser,"pendiente");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return tareasPendientes.stream()
                .map(a -> new TareaDTO(
                        a.getFechaInicio(),
                        a.getFechaFin(),
                        a.getFechaEliminada(),
                        a.getFechaLimite(),
                        a.getTipoTarea().getId(),
                        a.getEstado(),
                        a.getTitulo(),
                        a.getDescripcion()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/calendario/tareas-general")
    @ResponseBody
    public List<TareaDTO> obtenerTareasGeneral(){
        Integer idUser = 2;
        List<Tarea> tareasPendientes = tareaService.obtenerTareasEstadoAndUsuario(idUser,"pendiente");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return tareasPendientes.stream()
                .map(a -> new TareaDTO(
                        a.getFechaInicio(),
                        a.getFechaFin(),
                        a.getFechaEliminada(),
                        a.getFechaLimite(),
                        a.getTipoTarea().getId(),
                        a.getEstado(),
                        a.getTitulo(),
                        a.getDescripcion()
                ))
                .collect(Collectors.toList());
    }
}
