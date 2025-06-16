package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.UsuarioDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.entities.TipoTareas;
import com.grupo01.java6.faal.repositories.TareaRepository;
import com.grupo01.java6.faal.repositories.TiposTareasRepository;
import com.grupo01.java6.faal.services.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tareas/jefe")
public class TareasJefeController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TiposTareasRepository tipoTareasRepository;

    @Autowired
    private LoginService loginService;

    @GetMapping("/asignar")
    public String mostrarFormularioAsignarTarea(Model model) {
        model.addAttribute("nuevaTarea", new Tarea());

       // List<UsuarioDTO> usuarios = loginService.getUsuariosDTOConRolUsuarioOVisitante();

        // LOG para depurar
//        System.out.println("Usuarios cargados: " + usuarios.size());
//        usuarios.forEach(u -> System.out.println(u.getId() + ": " + u.getNombre() + " - " + u.getEmail()));

     //   model.addAttribute("usuarios", usuarios);

        List<TipoTareas> tipos = tipoTareasRepository.findAll();
        model.addAttribute("tiposTarea", tipos);

        return "tareasJefe";
    }

    // Procesar el envío del formulario
    @PostMapping("/asignar")
    public String asignarTarea(@ModelAttribute("nuevaTarea") Tarea tarea, Model model) {
        try {
            Integer idLogin = tarea.getLoginTarea().getId();

            if (idLogin == null) {
                throw new IllegalArgumentException("Debe seleccionar un usuario válido.");
            }

            Login usuario = loginService.getUserById(idLogin);

            if (usuario == null) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + idLogin);
            }

            TipoTareas tipo = tipoTareasRepository.findById(tarea.getTipoTarea().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de tarea no válido"));

            tarea.setLoginTarea(usuario);
            tarea.setTipoTarea(tipo);
            tarea.setFechaEliminada(null);

            tareaRepository.save(tarea);

            model.addAttribute("nuevaTarea", new Tarea());

            model.addAttribute("mensajeExito", "Tarea asignada correctamente.");

        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("nuevaTarea", tarea);
        }

        // Recargar listas necesarias para el formulario
        List<UsuarioDTO> usuarios = loginService.getUsuariosDTOConRolUsuarioOVisitante();
        model.addAttribute("usuarios", usuarios);

        List<TipoTareas> tipos = tipoTareasRepository.findAll();
        model.addAttribute("tiposTarea", tipos);

        return "tareasJefe";
    }


}