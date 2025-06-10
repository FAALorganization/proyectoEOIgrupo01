package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Documento;
import com.grupo01.java6.faal.services.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/documentacion")
public class DocumentacionController {

    private final DocumentoService documentoService;

    @GetMapping("/descargar/{id}")
    public ResponseEntity<Resource> descargarDocumento(@PathVariable Long id) {
        Documento documento = documentoService.obtenerPorId(id);
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

    @PostMapping("/borrar")
    public ResponseEntity<String> borrarDocumento(@RequestParam Long idDocumento) { // Cambié idProyecto por idDocumento
        boolean eliminado = documentoService.eliminarDocumento(idDocumento);

        return eliminado ? ResponseEntity.ok("Documento eliminado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el documento");
    }

    @PostMapping("/subir")
    public ResponseEntity<String> subirDocumento(@RequestParam Long idProyecto,
                                                 @RequestParam("archivo") MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe subir un archivo válido");
        }

        boolean guardado = documentoService.guardarDocumento(Math.toIntExact(idProyecto), archivo);

        return guardado ? ResponseEntity.ok("Documento subido correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el documento");
    }
}