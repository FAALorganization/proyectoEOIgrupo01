package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Equipo;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.EquipoService;
import com.grupo01.java6.faal.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/gestionEquipos")
    public String listarEquipos(Model model) {
        Integer idJefe = equipoService.obtenerIdJefeActual();
        List<Equipo> equipos = equipoService.obtenerTodosLosEquipos();
        Equipo equipo = new Equipo();
        System.out.println("Equipo inicializado: " + equipo); // Verifica si es null o no
        model.addAttribute("equipos", equipos);
        model.addAttribute("equipo", equipo);
        model.addAttribute("logins", loginService.obtenerTrabajadoresPorJefe(idJefe));
        return "gestionEquipos";
    }

    @PostMapping("/gestionEquipos/crear")
    public String crearEquipo(@ModelAttribute Equipo equipo) {
        // Transformar listaLoginIds a listaLogin para guardar correctamente la relación
        Set<Login> miembros = new HashSet<>();
        if (equipo.getListaLoginIds() != null) {
            for (Integer idLogin : equipo.getListaLoginIds()) {
                Login login = loginService.obtenerPorId(idLogin);
                if (login != null) {
                    miembros.add(login);
                }
            }
        }
        equipo.setListaLogin(miembros);

        equipoService.guardarEquipo(equipo);
        return "redirect:/gestionEquipos";
    }

    @GetMapping("/gestionEquipos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        Integer idJefe = equipoService.obtenerIdJefeActual();
        Equipo equipo = equipoService.obtenerEquipoPorId(id);

        // Para que el formulario marque los miembros seleccionados
        if (equipo.getListaLogin() != null) {
            Set<Integer> listaLoginIds = new HashSet<>();
            for (Login login : equipo.getListaLogin()) {
                listaLoginIds.add(login.getId());
            }
            equipo.setListaLoginIds(listaLoginIds);
        }

        model.addAttribute("equipo", equipo);
        model.addAttribute("logins", loginService.obtenerTrabajadoresPorJefe(idJefe));
        return "gestionEquipos";
    }

    @PostMapping("/gestionEquipos/editar/{id}")
    public String editarEquipo(@PathVariable Integer id, @ModelAttribute Equipo equipo) {
        equipo.setId(id);

        Set<Login> miembros = new HashSet<>();
        if (equipo.getListaLoginIds() != null) {
            for (Integer idLogin : equipo.getListaLoginIds()) {
                Login login = loginService.obtenerPorId(idLogin);
                if (login != null) {
                    miembros.add(login);
                }
            }
        }
        equipo.setListaLogin(miembros);

        equipoService.guardarEquipo(equipo);
        return "redirect:/gestionEquipos";
    }

    @GetMapping("/gestionEquipos/eliminar/{id}")
    public String eliminarEquipo(@PathVariable Integer id) {
        equipoService.eliminarEquipo(id);
        return "redirect:/gestionEquipos";
    }
}
