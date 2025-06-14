package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import com.grupo01.java6.faal.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class RegistroController {
    private final DetallesdeusuarioService detallesdeusuarioService;
    private final LoginService loginService;

    public RegistroController(DetallesdeusuarioService detallesdeusuarioService, LoginService loginService) {
        this.detallesdeusuarioService = detallesdeusuarioService;
        this.loginService = loginService;
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
                    List<String> columnas = Arrays.asList(linea.split(","));
                    String nombre = columnas.get(0).trim();
                    String apellido = columnas.get(1).trim();
                    String email = columnas.get(2).trim();
                    String nombreJefe = columnas.size() > 3 ? columnas.get(3).trim() : null; // Detectar nombre del jefe
                    String apellidoJefe = columnas.size() > 4 ? columnas.get(4).trim() : null;

                    Detallesdeusuario detallesdeuser = new Detallesdeusuario();
                    detallesdeuser.setNombre(nombre);
                    detallesdeuser.setApellidos(apellido);

                    detallesdeusuarioService.guardarEntityEntity(detallesdeuser);

                    // Crear Login asociado
                    Login login = new Login();
                    login.setActivo(true);
                    login.setIdDetallesDeUsuario(detallesdeuser);
                    login.setEmailPrimario(email);
                    login.setToken(generarTokenAleatorio(10)); // Genera password temporal

                    if (nombreJefe != null && !nombreJefe.isEmpty() && apellidoJefe != null && !apellidoJefe.isEmpty()) {
                        Detallesdeusuario jefeDetalles = detallesdeusuarioService.findByNombreAndApellidos(nombreJefe, apellidoJefe);
                        if (jefeDetalles != null) {
                            boolean esJefe = detallesdeusuarioService.verificarRolJefe(jefeDetalles.getId());
                            if (esJefe) {
                                Login jefeLogin = loginService.obtenerPorDetallesUsuario(jefeDetalles);
                                if (jefeLogin != null) {
                                    login.setJefeLogin(jefeLogin); // Asociar jefe al usuario
                                }
                            }
                        }
                    }

                    loginService.guardarLogin(login);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al procesar el archivo: " + file.getOriginalFilename());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok("Archivos CSV procesados y usuarios creados correctamente.");
    }

    private String generarTokenAleatorio(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return sb.toString();
    }
}
