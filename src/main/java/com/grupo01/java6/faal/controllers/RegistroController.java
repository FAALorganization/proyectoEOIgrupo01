package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class RegistroController {
    private final DetallesdeusuarioService detallesdeusuarioService;

    public RegistroController(DetallesdeusuarioService detallesdeusuarioService) {
        this.detallesdeusuarioService = detallesdeusuarioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("archivo") MultipartFile[] files) {

        if (files.length == 0) {
            return ResponseEntity.badRequest().body("No se envió ningún archivo.");
        }

        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().endsWith(".csv")) {
                return ResponseEntity.badRequest().body("Todos los archivos deben ser CSV.");
            }

            try {
                String contenido = new String(file.getBytes());
                List<String> lineas = Arrays.asList(contenido.split("\\R"));
                for (String linea : lineas) {
                      List <String> columnas = Arrays.asList(linea.split(","));
                      String nombre = columnas.getFirst().trim();
                      String apellido = columnas.get(1).trim();
                      String email = columnas.getLast().trim();

                      Detallesdeusuario detallesdeuser = new Detallesdeusuario();
                      detallesdeuser.setNombre(nombre);
                      detallesdeuser.setApellidos(apellido);

                      detallesdeusuarioService.guardarEntityEntity(detallesdeuser);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo: " + file.getOriginalFilename());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok("Archivos CSV recibidos correctamente.");
    }

}
