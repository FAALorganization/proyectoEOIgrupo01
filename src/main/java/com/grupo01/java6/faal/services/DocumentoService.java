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
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProyectoRepository proyectoRepository;
    private final String CARPETA_SUBIDA = "uploads/";

    /** 🔹 Obtener un documento por su ID */
    public Documento obtenerPorId(Long id) {
        return documentoRepository.findById(id).orElse(null);
    }

    /** 🔹 Obtener el recurso de un documento para su descarga */
    public Resource obtenerRecurso(Documento documento) {
        try {
            Path rutaArchivo = Paths.get(documento.getRutaArchivo());
            return new UrlResource(rutaArchivo.toUri());
        } catch (Exception e) {
            return null;
        }
    }

    /** 🔹 Subir documento asociado a un proyecto */
    public boolean guardarDocumento(Proyecto proyecto, MultipartFile archivo) {
        try {
            if (archivo.isEmpty()) {
                System.out.println("⚠️ Error: El archivo está vacío.");
                return false;
            }

            // Crear carpeta si no existe
            Path carpeta = Paths.get(CARPETA_SUBIDA);
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            // Limpiar y generar nombre único
            String nombreOriginal = org.springframework.util.StringUtils.cleanPath(archivo.getOriginalFilename());
            String nombreArchivo = System.currentTimeMillis() + "_" + nombreOriginal;

            Path rutaDestino = carpeta.resolve(nombreArchivo);
            archivo.transferTo(rutaDestino.toFile());

            Documento nuevoDocumento = new Documento();
            nuevoDocumento.setNombre(nombreOriginal);
            nuevoDocumento.setRutaArchivo(rutaDestino.toString());
            nuevoDocumento.setProyecto(proyecto);

            documentoRepository.save(nuevoDocumento);
            System.out.println("✅ Documento guardado correctamente: " + nuevoDocumento.getNombre());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠️ Error inesperado al subir el archivo.");
            return false;
        }
    }

    /** 🔹 Eliminar documento y su archivo del servidor */
    public boolean eliminarDocumento(Long idDocumento) {
        Documento documento = documentoRepository.findById(idDocumento).orElse(null);
        if (documento != null) {
            Path rutaArchivo = Paths.get(documento.getRutaArchivo());

            try {
                Files.deleteIfExists(rutaArchivo); // Borra el archivo físico
            } catch (IOException e) {
                System.out.println("⚠️ Error al borrar el archivo del servidor.");
                return false;
            }

            documentoRepository.delete(documento); // Borra el documento en la BD
            System.out.println("✅ Documento eliminado correctamente.");
            return true;
        }
        return false;
    }

    public List<Proyecto> obtenerProyectosConDocumentos() {
        return proyectoRepository.findAllConDocumentos(); // Esto ya carga los documentos
    }

}