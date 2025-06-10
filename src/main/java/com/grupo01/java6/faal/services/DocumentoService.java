package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Documento;
import com.grupo01.java6.faal.entities.Proyecto;
import com.grupo01.java6.faal.repositories.DocumentoRepository;
import com.grupo01.java6.faal.repositories.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProyectoRepository proyectoRepository; // Agregamos ProyectoRepository

    public Documento obtenerPorId(Long id) {
        return documentoRepository.findById(id).orElse(null);
    }

    public Resource obtenerRecurso(Documento documento) {
        try {
            Path rutaArchivo = Paths.get(documento.getRutaArchivo());
            return new UrlResource(rutaArchivo.toUri());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean eliminarDocumento(Long idDocumento) {
        Documento documento = documentoRepository.findById(idDocumento).orElse(null);
        if (documento != null) {
            Path rutaArchivo = Paths.get(documento.getRutaArchivo());

            try {
                Files.deleteIfExists(rutaArchivo); // Borra el archivo del servidor
            } catch (IOException e) {
                return false; // Si hay un error al borrar el archivo, retorna falso
            }

            documentoRepository.delete(documento); // Borra el documento de la base de datos
            return true;
        }
        return false;
    }

    public boolean guardarDocumento(Integer idProyecto, MultipartFile archivo) {
        try {
            if (archivo.isEmpty()) {
                System.out.println("⚠️ Error: El archivo está vacío.");
                return false;
            }

            String nombreArchivo = archivo.getOriginalFilename();
            Path rutaDestino = Paths.get("uploads/" + nombreArchivo);
            archivo.transferTo(rutaDestino.toFile());

            Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);
            if (proyecto == null) {
                System.out.println("⚠️ Error: Proyecto no encontrado con ID " + idProyecto);
                return false;
            }

            Documento nuevoDocumento = new Documento();
            nuevoDocumento.setNombre(nombreArchivo);
            nuevoDocumento.setRutaArchivo(rutaDestino.toString());
            nuevoDocumento.setProyecto(proyecto);

            documentoRepository.save(nuevoDocumento);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 🔍 Imprime el error en la consola
            System.out.println("⚠️ Error inesperado al subir el archivo.");
            return false;
        }
    }
}