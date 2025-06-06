package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.UsuarioDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.entities.TipoTareas;
import com.grupo01.java6.faal.repositories.TareaRepository;
import com.grupo01.java6.faal.repositories.TipoTareasRepository;
import com.grupo01.java6.faal.services.LoginService;

import org.hibernate.Hibernate;
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
    private TipoTareasRepository tipoTareasRepository;

    @Autowired
    private LoginService loginService;

    // Mostrar el formulario para asignar tareas
    @GetMapping("/asignar")
    public String mostrarFormularioAsignarTarea(Model model) {
        model.addAttribute("nuevaTarea", new Tarea());

        // Lista de usuarios para el combo
        List<UsuarioDTO> usuarios = loginService.getUsuariosDTO();
        model.addAttribute("usuarios", usuarios);

        // Lista de tipos de tarea
        List<TipoTareas> tipos = tipoTareasRepository.findAll();
        model.addAttribute("tiposTarea", tipos);

        return "tareasJefe";
    }

    // Procesar el envío del formulario
    @PostMapping("/asignar")
    public String asignarTarea(@ModelAttribute("nuevaTarea") Tarea tarea) {

        String emailUsuario = tarea.getLoginTarea().getEmailPrimario();
        if (emailUsuario == null || emailUsuario.isEmpty()) {
            throw new IllegalArgumentException("Email de usuario no puede ser vacío");
        }

        // Buscar usuario por email
        Login usuario = loginService.getUserByEmail(emailUsuario);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con email: " + emailUsuario);
        }

        // Evita errores de Lazy Fetching
        Hibernate.initialize(usuario.getIdDetallesDeUsuario());

        // Obtener tipo de tarea
        TipoTareas tipo = tipoTareasRepository.findById(tarea.getTipoTarea().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de tarea no válido"));

        tarea.setLoginTarea(usuario);
        tarea.setTipoTarea(tipo);
        tarea.setFechaEliminada(null);

        tareaRepository.save(tarea);

        return "redirect:/tareas";
    }

}
