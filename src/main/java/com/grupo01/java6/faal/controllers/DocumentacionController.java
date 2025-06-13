package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Documento;
import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.repositories.DocumentoRepository;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import com.grupo01.java6.faal.services.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/documentacion")
public class DocumentacionController {

    private final DocumentoService documentoService;
    private final ProyectoRepository proyectoRepository;
    private final DocumentoRepository documentoRepository;

    /** 🔹 Ver todos los documentos de un proyecto */
    @GetMapping("/ver/{idProyecto}")
    public ResponseEntity<List<Documento>> verDocumentos(@PathVariable Integer idProyecto) {
        List<Documento> documentos = documentoRepository.findByProyecto_Id(idProyecto);
        return ResponseEntity.ok(documentos);
    }

    /** 🔹 Subir documento asociado a un proyecto */
    @PostMapping("/subir")
    public String subirDocumentos(@RequestParam Long idProyecto,
                                  @RequestParam("archivos") MultipartFile[] archivos,
                                  RedirectAttributes redirectAttributes) {

        Proyecto proyecto = proyectoRepository.findById(Math.toIntExact(idProyecto)).orElse(null);
        if (proyecto == null) {
            redirectAttributes.addFlashAttribute("error", "❌ Proyecto no encontrado.");
            return "redirect:/documentacion";
        }

        boolean algunoFalló = false;

        for (MultipartFile archivo : archivos) {
            if (!archivo.isEmpty()) {
                boolean guardado = documentoService.guardarDocumento(proyecto, archivo);
                if (!guardado) {
                    algunoFalló = true;
                }
            }
        }

        if (archivos.length == 0) {
            redirectAttributes.addFlashAttribute("error", "⚠️ No se seleccionaron archivos para subir.");
        } else if (algunoFalló) {
            redirectAttributes.addFlashAttribute("error", "⚠️ Algunos documentos no se pudieron subir.");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "✅ Documentos subidos correctamente.");
        }

        return "redirect:/documentacion";
    }


    /** 🔹 Descargar documento */
    @GetMapping("/descargar/{idDocumento}")
    public ResponseEntity<Resource> descargarDocumento(@PathVariable Integer idDocumento) {
        Documento documento = documentoService.obtenerPorId(idDocumento);
        if (documento == null || documento.getRutaArchivo() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Resource recurso = documentoService.obtenerRecurso(documento);
        if (recurso == null || !recurso.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNombre() + "\"")
                .body(recurso);
    }

    /** 🔹 Borrar documento */
    @PostMapping("/borrar")
    public ResponseEntity<String> borrarDocumento(@RequestParam Integer idDocumento) {
        boolean eliminado = documentoService.eliminarDocumento(idDocumento);
        return eliminado ? ResponseEntity.ok("Documento eliminado correctamente.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el documento.");
    }
}