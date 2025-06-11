package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import com.grupo01.java6.faal.services.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoRepository proyectoRepository;
    private final DocumentoService documentoService;  // <-- Inyección del servicio

    @GetMapping("/documentacion")
    public String mostrarDocumentacion(Model model) {
        List<Proyecto> proyectosGenerales = documentoService.obtenerProyectosConDocumentos();
        model.addAttribute("proyectosGenerales", proyectosGenerales);
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
}
