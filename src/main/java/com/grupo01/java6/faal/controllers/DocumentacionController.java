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
    @ResponseBody
    public ResponseEntity<String> subirDocumentosAjax(@RequestParam Integer idProyecto,
                                                      @RequestParam("archivos") MultipartFile[] archivos) {
        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);
        if (proyecto == null) {
            return ResponseEntity.badRequest().body("❌ Proyecto no encontrado.");
        }

        if (archivos == null || archivos.length == 0) {
            return ResponseEntity.badRequest().body("⚠️ No se seleccionaron archivos para subir.");
        }

        boolean algunoFalló = false;

        for (MultipartFile archivo : archivos) {
            if (!archivo.isEmpty()) {
                String nombreLimpio = limpiarNombreArchivo(archivo.getOriginalFilename());
                boolean guardado = documentoService.guardarDocumento(proyecto, archivo, nombreLimpio);
                if (!guardado) {
                    algunoFalló = true;
                }
            }
        }

        if (algunoFalló) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("⚠️ Algunos documentos no se pudieron subir.");
        }

        return ResponseEntity.ok("✅ Documentos subidos correctamente.");
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
    public String borrarDocumento(@RequestParam Integer idDocumento,
                                  RedirectAttributes redirectAttributes) {
        boolean eliminado = documentoService.eliminarDocumento(idDocumento);
        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "✅ Documento eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ No se pudo eliminar el documento.");
        }
        return "redirect:/documentacion";
    }

    /** 🔹 Sanitiza el nombre del archivo eliminando caracteres no seguros */
    private String limpiarNombreArchivo(String nombreOriginal) {
        if (nombreOriginal == null) return "archivo_desconocido";
        return nombreOriginal.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
