package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.Equipo;
import com.grupo01.java6.faal.services.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping("/gestionEquipos")
    public String showGestionEquipos(Model model) {
        List<Equipo> equipos = equipoService.obtenerTodosLosEquipos();
        Map<Integer, List<CompaneroDTO>> nombresPorEquipo = equipoService.obtenerNombresPorEquipo();

        model.addAttribute("equipos", equipos);
        model.addAttribute("nombresPorEquipo", nombresPorEquipo);

        return "gestionEquipos";
    }

}