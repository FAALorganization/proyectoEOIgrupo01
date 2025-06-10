package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.ProyectoAsignadoDTO;
import com.grupo01.java6.faal.dtos.UsuarioDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.entities.AsignacionProyectoUsuario;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import com.grupo01.java6.faal.repositories.AsignacionProyectoUsuarioRepository;
import com.grupo01.java6.faal.services.LoginService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoRepository proyectoRepository;
    private final AsignacionProyectoUsuarioRepository asignacionProyectoUsuarioRepository;
    private final LoginService loginService;

    @GetMapping("/documentacion")
    public String mostrarDocumentacion(Model model, Principal principal) {
        List<Proyecto> proyectosGenerales = proyectoRepository.findAll(); // Debe recuperar todos los proyectos

        model.addAttribute("proyectosGenerales", proyectosGenerales);
        return "documentacion";
    }

    @GetMapping("/gestion-proyectos")
    public String mostrarGestionProyectos(Model model) {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        List<UsuarioDTO> usuarios = loginService.getUsuariosDTOConRolUsuarioOVisitante();

        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuarios", usuarios);

        return "gestionProyectos";
    }

    @PostMapping("/proyectos/crear")
    public String crearProyecto(@RequestParam String nombre,
                                @RequestParam(required = false) String descripcion,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
                                RedirectAttributes redirectAttributes) {
        Proyecto nuevoProyecto = new Proyecto();
        nuevoProyecto.setNombre(nombre);
        nuevoProyecto.setDescripcion(descripcion);
        nuevoProyecto.setFechaInicio(fechaInicio);
        nuevoProyecto.setFechaFin(fechaFin);

        proyectoRepository.save(nuevoProyecto);
        redirectAttributes.addFlashAttribute("mensaje", "Proyecto creado correctamente");

        return "redirect:/documentacion"; // Aquí hacemos que la vista se actualice
    }

    @PostMapping("/proyectos/asignar")
    public String asignarProyectoAUsuario(@RequestParam Integer idProyecto,
                                          @RequestParam Integer idUsuario,
                                          RedirectAttributes redirectAttributes) {
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);
        Login usuario = loginService.getUserById(idUsuario);

        if (proyecto == null || usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Proyecto o usuario no encontrado");
            return "redirect:/gestion-proyectos";
        }

        // Comprobar si ya existe asignación (opcional, evitar duplicados)
        boolean yaAsignado = asignacionProyectoUsuarioRepository
                .existsByProyecto_IdAndUsuario_Id(idProyecto, idUsuario);

        if (yaAsignado) {
            redirectAttributes.addFlashAttribute("error", "Proyecto ya asignado a este usuario");
            return "redirect:/gestion-proyectos";
        }

        AsignacionProyectoUsuario asignacion = new AsignacionProyectoUsuario();
        asignacion.setProyecto(proyecto);
        asignacion.setUsuario(usuario);
        asignacionProyectoUsuarioRepository.save(asignacion);

        redirectAttributes.addFlashAttribute("mensaje", "Proyecto asignado correctamente");
        return "redirect:/gestion-proyectos";
    }
}
