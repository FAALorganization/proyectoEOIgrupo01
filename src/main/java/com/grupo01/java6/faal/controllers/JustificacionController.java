package com.grupo01.java6.faal.controllers;


import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.AusenciasRepository;
import com.grupo01.java6.faal.services.AusenciasService;
import com.grupo01.java6.faal.services.LoginService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@Slf4j
public class JustificacionController {

    private final AusenciasService ausenciaService;
    private final LoginService loginService;

    public JustificacionController(AusenciasService ausenciaService, LoginService loginService) {
        this.ausenciaService = ausenciaService;
        this.loginService = loginService;
    }

    @PostMapping("/gestion/justificacion")
    public ResponseEntity<String> recibirJustificacion(
       @RequestParam("fecha") String fecha,
       @RequestParam("asunto") String asunto,
       @RequestParam("descripcion") String descripcion,
       @RequestParam(value = "archivos", required = false) MultipartFile[] archivos
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernName = auth.getName();
        Login loginUser = loginService.obtainUser(usernName);
        String token = loginUser.getToken();

        try {

//            String userHome = System.getProperty("user.home");// Definir ruta carpeta 'files' en home usuario
//            Path carpeta = Paths.get(userHome, "files");
            // Path filesPath = Paths.get(System.getProperty("user.home"), "files");

            Path carpeta = Paths.get("/var/lib/data/");
            if (!Files.exists(carpeta)) {// Crear carpeta si no existe
                Files.createDirectories(carpeta);
            }

            StringBuilder archivosNombresBuilder = new StringBuilder();
            int contador = 0;
            if (archivos != null) {
                for (MultipartFile archivo : archivos) {
                    if (!archivo.isEmpty()) {
                        String originalFilename = archivo.getOriginalFilename();
                        String extension = "";
                        if (originalFilename != null && originalFilename.contains(".")) {
                            extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
                        }

                        String nuevoNombre = token + "." + fecha + "." + contador + ".just." + extension; // Construir nuevo nombre
                        nuevoNombre = nuevoNombre.replace("-", "_").replace(" al ", ".");
                        Path destino = carpeta.resolve(nuevoNombre);// Resolver la ruta con nuevo nombre
                        archivo.transferTo(destino.toFile());// Guardar el archivo en destino con el nuevo nombre
                        archivosNombresBuilder.append(nuevoNombre).append("|");// Añadir al string builder
                        contador += 1;
                    }
                }
            }


// Convertimos a String y eliminamos la barra final si existe
            String archivosNombres = archivosNombresBuilder.toString();
            if (archivosNombres.endsWith("|")) {
                archivosNombres = archivosNombres.substring(0, archivosNombres.length() - 1);
            }

            JustificacionDTO dto = new JustificacionDTO(fecha, asunto, descripcion, archivosNombres);
            ausenciaService.justificarAusencia(dto, auth);
            return ResponseEntity.ok("Justificación guardada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la justificación.");
        }

    }

    @PostMapping("/gestionVRes/aprobar-justificacion")
    @ResponseBody
    public ResponseEntity<byte[]> recibirFecha(@RequestBody String fecha) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            String[] fechas = fecha.split("\\.");
            String nombre = fechas[0];
            LocalDate fechaIni = LocalDate.parse(fechas[1], formatter);
            LocalDate fechaFin = LocalDate.parse(fechas[2], formatter);

            Optional<Ausencias> ausenciaOpt = ausenciaService.obtainAusenciaByFechaInicioFechaFinId(fechaIni, fechaFin,nombre);
            if (ausenciaOpt.isPresent()) {
                Ausencias ausencia = ausenciaOpt.get();
                String[] archivosList = ausencia.getDocumentos().split("\\|");
                // Path filesPath = Paths.get(System.getProperty("user.home"), "files");

                Path filesPath = Paths.get("/var/lib/data/");

                // Crear zip en memoria con ByteArrayOutputStream
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (ZipOutputStream zos = new ZipOutputStream(baos)) {
                    for (String nombreArchivo : archivosList) {
                        Path archivo = filesPath.resolve(nombreArchivo);
                        if (Files.exists(archivo)) {

                            String extension = "";
                            int i = nombreArchivo.lastIndexOf('.');
                            if (i > 0) {
                                extension = nombreArchivo.substring(i);
                            }
                            int lengthString = (int)(Math.random() * 6) + 15;
                            String nombreNuevo = generarNombreAleatorio(lengthString) + extension;

                            zos.putNextEntry(new ZipEntry(nombreNuevo));
                            Files.copy(archivo, zos);
                            zos.closeEntry();
                        }
                    }
                }

                byte[] zipBytes = baos.toByteArray();
                //System.out.println("Tamaño del ZIP generado (bytes): " + zipBytes.length);

                String nombreZip = "justificantes_" + fecha.replace(".", "_") + ".zip";

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreZip + "\"")
                        .body(zipBytes);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public String generarNombreAleatorio(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return sb.toString();
    }

    @PostMapping("/gestionVRes/resolucion")
    public ResponseEntity<?> recibirResolucion(@RequestBody Map<String, Map<String, Boolean>> respuestas) {

        respuestas.forEach((nombre, decisiones) -> decisiones.forEach((fecha, valor) -> {
            //System.out.println("Respuesta: " + nombre + "," + decisiones + "," + valor + "," + fecha);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {

                List<String> fechas = List.of(fecha.split("\\."));
                LocalDate fechaIni = LocalDate.parse(fechas.getFirst(), formatter);
                LocalDate fechaFin = LocalDate.parse(fechas.getLast(), formatter);

                Optional<Ausencias> ausenciaOpt = ausenciaService.obtainAusenciaByFechaInicioFechaFinId(fechaIni, fechaFin,nombre.replace(".","-"));
                if (ausenciaOpt.isPresent()) {
                    Ausencias ausencia = ausenciaOpt.get();
                    System.out.println("Ausencia: " + ausencia.getTiposAusencias().getId() + "," + ausencia.getAprobado() + "," + valor);
                    if (ausencia.getTiposAusencias().getId() == 1) {
                        if (Boolean.TRUE.equals(valor)) {
                            ausenciaService.cambiarAprobado(ausencia, true);
                        } else {
                            ausenciaService.eliminateHoliday(ausencia);
                        }
                    } else if (ausencia.getTiposAusencias().getId() == 2) {
                        if (Boolean.TRUE.equals(valor)) {
                            ausenciaService.cambiarAprobado(ausencia, true);
                        } else {
                            ausencia.setJustificacion(null);
                            ausencia.setDocumentos(null);
                            ausenciaService.guardarEntidadDto(ausencia);
                        }
                    }

                    //System.out.println(nombre + " ||| Fecha Ini: " + fechas.getFirst() + " ||| Fecha Fin: " + fechas.getLast() + " ||| Valor: " + valor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }));

        return ResponseEntity.ok().build();
    }
}
