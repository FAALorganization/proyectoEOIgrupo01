package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.Equipo;
import com.grupo01.java6.faal.services.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        // Obtener subordinados del jefe actual
        List<CompaneroDTO> subordinados = equipoService.obtenerSubordinadosDelJefeActual();

        model.addAttribute("equipos", equipos);
        model.addAttribute("nombresPorEquipo", nombresPorEquipo);
        model.addAttribute("subordinados", subordinados);

        return "gestionEquipos";
    }
    @PostMapping("/gestionEquipos/editar")
    public String editarEquipo(@RequestParam Integer equipoId, @RequestParam String nombre, @RequestParam String descripcion) {
        equipoService.editarEquipo(equipoId, nombre, descripcion);
        return "redirect:/gestionEquipos"; // Redirige a la vista principal
    }
    @PostMapping("/gestionEquipos/quitarMiembro")
    public String quitarMiembroDelEquipo(@RequestParam Integer equipoId, @RequestParam Integer loginId) {
        equipoService.quitarMiembro(equipoId, loginId);
        return "redirect:/gestionEquipos";
    }
    @PostMapping("/gestionEquipos/anadirMiembro")
    public String anadirMiembroAlEquipo(@RequestParam Integer equipoId, @RequestParam List<Integer> nuevosLoginIds) {
        equipoService.anadirMiembros(equipoId, nuevosLoginIds);
        return "redirect:/gestionEquipos";
    }
    @PostMapping("/gestionEquipos/crear")
    public String crearEquipo(@RequestParam String nombre, @RequestParam String descripcion, @RequestParam List<Integer> loginIds) {
        equipoService.crearEquipo(nombre, descripcion, loginIds);
        return "redirect:/gestionEquipos";
    }
}