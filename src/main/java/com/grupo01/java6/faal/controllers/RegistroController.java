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
        StringBuilder tokensGenerados = new StringBuilder();

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

                    Detallesdeusuario detallesdeuser = new Detallesdeusuario();
                    detallesdeuser.setNombre(nombre);
                    detallesdeuser.setApellidos(apellido);

                    detallesdeusuarioService.guardarEntidadEntidad(detallesdeuser);

                    Login login = new Login();
                    login.setActivo(true);
                    login.setIdDetallesDeUsuario(detallesdeuser);
                    login.setEmailPrimario(email);
                    String token = generarTokenAleatorio(10);
                    login.setToken(token);

                    loginService.guardarLogin(login);

                    tokensGenerados.append("Usuario: ").append(email).append(", Token: ").append(token).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al procesar el archivo: " + file.getOriginalFilename());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok("Usuarios creados correctamente. Tokens:\n" + tokensGenerados.toString());
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
