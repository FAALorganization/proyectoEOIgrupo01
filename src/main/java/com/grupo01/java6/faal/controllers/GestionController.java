package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.AusenciaDTO;
import com.grupo01.java6.faal.dtos.EmpleadoConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.AusenciasService;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class GestionController {


    private final LoginService loginService;
    private final RolesService rolesService;
    private final AusenciasService ausenciasService;

    public GestionController(LoginService loginService, RolesService rolesService, AusenciasService ausenciasService) {
        this.loginService = loginService;
        this.rolesService = rolesService;
        this.ausenciasService = ausenciasService;
    }

    @GetMapping("/gestion")
    public String showgestionVacaciones(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUser = auth.getName();
        Login loginUser = loginService.obtainUser(correoUser);

        Integer idUser = loginUser.getId();

        String rol = rolesService.findRolById(idUser);

        model.addAttribute("rol", rol);

        return "gestionVacaciones";
    }

    @GetMapping("/gestion/ausencias")
    @ResponseBody
    public List<AusenciaDTO> obtenerAusenciasPorLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUser = auth.getName();
        Login loginUser = loginService.obtainUser(correoUser);

        Integer idUser = loginUser.getId();
        List<Ausencias> ausencias = ausenciasService.findVacaciones(idUser);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return ausencias.stream()
                .map(a -> new AusenciaDTO(
                        a.getTiposAusencias().getId(),
                        a.getAprobado(),
                        a.getFechaInicio().format(formatter),
                        a.getFechaFin().format(formatter),
                        a.getJustificacion() != null && a.getJustificacion().length() > 3
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/gestion/companeros")
    @ResponseBody
    public List<NombreDTO> obtenerCompaneros() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        return loginService.obtenerCompaneros(correo);
    }

    @GetMapping("/companeros-con-ausencias")
    @ResponseBody
    public List<EmpleadoConAusenciasDTO> getCompanerosConAusencias(String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        return loginService.obtenerCompanerosConAusenciasAgrupados(correo);
    }
}
