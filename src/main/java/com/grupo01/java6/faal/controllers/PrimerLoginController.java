package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class PrimerLoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    public PrimerLoginController(LoginService loginService, PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
    }

    // Paso 1: Validar email + token (del registro que dio el admin)
    @PostMapping("/validar-token")
    public ResponseEntity<?> validarTokenYEmail(@RequestBody Map<String, String> datos) {
        System.out.println("Datos recibidos: " + datos);
        String email = datos.get("email");
        String token = datos.get("token");

        Login login = loginService.getUserByEmail(email);

        if (login == null || login.getToken() == null || !login.getToken().equals(token)) {
            System.out.println("Email o token inválidos");
            return ResponseEntity.badRequest().body("Email o token inválidos");
        }

        return ResponseEntity.ok("Token válido");
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");
        String nuevaPass = datos.get("nuevaPass");

        Login login = loginService.getUserByEmail(email);

        if (login == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        login.setPassword(passwordEncoder.encode(nuevaPass));

        loginService.actualizarLogin(login);

        System.out.println("Contraseña actualizada para: " + login.getEmailPrimario());
        System.out.println("Nueva pass (cruda): " + nuevaPass);

        return ResponseEntity.ok("Contraseña actualizada con éxito.");
    }

}