package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.ProyectoDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import com.grupo01.java6.faal.services.DocumentoService;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoRepository proyectoRepository;
    private final ProyectoService proyectoService;
    private final DocumentoService documentoService;
    private final LoginService loginService;

    @GetMapping("/documentacion")
    public String mostrarDocumentacion(@RequestParam(required = false) String nombreProyecto,
                                       Model model, Principal principal) {

        List<ProyectoDTO> proyectosDTO;
        if (nombreProyecto != null && !nombreProyecto.isEmpty()) {
            proyectosDTO = proyectoService.buscarProyectosDTOporNombre(nombreProyecto);
        } else {
            proyectosDTO = proyectoService.obtenerProyectosDTO();
        }

        model.addAttribute("proyectos", proyectosDTO);

        Login usuarioActual = loginService.getUserByEmail(principal.getName());
        model.addAttribute("usuario", usuarioActual);

        boolean esJefe = usuarioActual.getRoles().stream()
                .anyMatch(r -> r.getNombre().trim().equalsIgnoreCase("Jefe"));
        model.addAttribute("esJefe", esJefe);

        return "documentacion";
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
        return "redirect:/documentacion";
    }

    @PostMapping("/proyectos/borrar")
    public String borrarProyecto(@RequestParam Integer idProyecto, Principal principal, RedirectAttributes redirectAttributes) {
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);
        if (proyecto == null) {
            redirectAttributes.addFlashAttribute("error", "❌ Proyecto no encontrado.");
            return "redirect:/documentacion";
        }

        // Obtener usuario actual
        Login usuarioActual = loginService.getUserByEmail(principal.getName());

        // Comprobar que el usuario es Jefe
        boolean esJefe = usuarioActual.getRoles().stream()
                .anyMatch(r -> r.getNombre().trim().equalsIgnoreCase("Jefe"));

        if (!esJefe) {
            redirectAttributes.addFlashAttribute("error", "❌ No tienes permisos para eliminar proyectos.");
            return "redirect:/documentacion";
        }

        // Borrar proyecto
        try {
            proyectoRepository.delete(proyecto);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Proyecto eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Error al eliminar el proyecto.");
        }

        return "redirect:/documentacion";
    }

    @GetMapping("/proyectos/todos")
    @ResponseBody
    public List<ProyectoDTO> obtenerTodosProyectos() {
        return proyectoService.obtenerProyectosDTO();
    }


}
