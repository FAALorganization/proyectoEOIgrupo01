package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.entities.TipoTareas;
import com.grupo01.java6.faal.services.TareaService;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.TiposTareasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private final LoginService loginService;
    private final TareaService tareaService;
    private final TiposTareasService tiposTareasService;

    public TareaController(TareaService tareaService, LoginService loginService, TiposTareasService tiposTareasService) {
        this.tareaService = tareaService;
        this.loginService = loginService;
        this.tiposTareasService = tiposTareasService;
    }

    @GetMapping
    public String listarTareas(Model model) {
        Login usuarioActual = obtainUser();
        model.addAttribute("usuarioActual", usuarioActual);

        model.addAttribute("pendientes", tareaService.obtenerPendientes()); // Ya filtra expiradas
        model.addAttribute("completadas", tareaService.obtenerCompletadas());
        model.addAttribute("eliminadas", tareaService.obtenerEliminadas());

        List<TipoTareas> tipos = tiposTareasService.findAll();
        model.addAttribute("tiposTarea", tipos);
        model.addAttribute("nuevaTarea", new Tarea());

        return "tareas";
    }

    private Login obtainUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Suponiendo que el username es el email
        return loginService.obtainUser(email);
    }

    @PostMapping("/agregar")
    public String agregarTarea(@ModelAttribute("nuevaTarea") Tarea nuevaTarea, BindingResult result, @RequestParam Integer idLogin) {
        if (result.hasErrors()) {
            return "tareas";
        }

        Login login = new Login();
        login.setId(idLogin);
        nuevaTarea.setLoginTarea(login);
        nuevaTarea.setFechaInicio(LocalDate.now());
        nuevaTarea.setEstado("pendiente"); //  Establece el estado inicial

        tareaService.guardarTarea(nuevaTarea);
        return "redirect:/tareas";
    }

    @PostMapping("/completar/{id}")
    public String completarTarea(@PathVariable Integer id) {
        tareaService.marcarComoCompletada(id);
        return "redirect:/tareas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Integer id) {
        tareaService.eliminarTarea(id);
        return "redirect:/tareas";
    }

    @PostMapping("/restaurar/{id}")
    public String restaurarTarea(@PathVariable Integer id) {
        tareaService.restaurarTarea(id);
        return "redirect:/tareas";
    }

    @PostMapping("/eliminar-definitivo/{id}")
    public String eliminarDefinitivamente(@PathVariable Integer id) {
        tareaService.eliminarDefinitivamente(id);
        return "redirect:/tareas";
    }
}