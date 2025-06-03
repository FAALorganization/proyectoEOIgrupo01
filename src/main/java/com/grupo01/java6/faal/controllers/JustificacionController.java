package com.grupo01.java6.faal.controllers;


import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.AusenciasService;
import com.grupo01.java6.faal.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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

            String userHome = System.getProperty("user.home");// Definir ruta carpeta 'files' en home usuario
            Path carpeta = Paths.get(userHome, "files");
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
//                    try {
//                        String nombreArchivoTxt = token + "." + fecha + "." + contador + "." + "descripcion.txt";
//                        nombreArchivoTxt = nombreArchivoTxt.replace("-", "_").replace(" al ", ".");
//                        Path archivoTxt = carpeta.resolve(nombreArchivoTxt);
//
//                        String[] asuntoNames = {"Enfermedad o Incapacidad temporal","Cita Médica","Permiso Personal","Permiso retribuido","Huelga","Baja maternidad","Reducción de jornada"};
//                        String asuntoName = asuntoNames[Integer.parseInt(asunto) - 1];
//                        Files.writeString(archivoTxt, "Asunto:\n " + asuntoName + "\nDescripción:\n" + descripcion, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        // Opcional: puedes retornar error o continuar
//                    }
                }
            }


// Convertimos a String y eliminamos la barra final si existe
            String archivosNombres = archivosNombresBuilder.toString();
            if (archivosNombres.endsWith("|")) {
                archivosNombres = archivosNombres.substring(0, archivosNombres.length() - 1);
            }

            JustificacionDTO dto = new JustificacionDTO(fecha, asunto, descripcion, archivosNombres);
            ausenciaService.justificarAusencia(dto);
            return ResponseEntity.ok("Justificación guardada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la justificación.");
        }

    }
}
