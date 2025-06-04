package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.services.TareaService;
import com.grupo01.java6.faal.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.time.LocalDate;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private final LoginService loginService;
    private final TareaService tareaService;

    public TareaController(TareaService tareaService, LoginService loginService) {
        this.tareaService = tareaService;
        this.loginService = loginService;
    }

    @GetMapping
    public String listarTareas(Model model) {
        Login usuarioActual = obtainUser(); // Método para obtener el usuario autenticado
        model.addAttribute("usuarioActual", usuarioActual); // Agregarlo al modelo
        model.addAttribute("pendientes", tareaService.obtenerPendientes());
        model.addAttribute("completadas", tareaService.obtenerCompletadas());
        model.addAttribute("eliminadas", tareaService.obtenerEliminadas());
        model.addAttribute("nuevaTarea", new Tarea());
        return "tareas";
    }

    private Login obtainUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Suponiendo que el username es el email
        return loginService.obtainUser(email);
    }

    @PostMapping("/agregar")
    public String agregarTarea(
            @ModelAttribute("nuevaTarea") Tarea nuevaTarea,
            BindingResult result,
            @RequestParam Integer idLogin
    ) {
        if (result.hasErrors()) {
            return "tareas";
        }

        Login login = new Login();
        login.setId(idLogin);
        nuevaTarea.setLoginTarea(login);
        nuevaTarea.setFechaInicio(LocalDate.now());

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
}