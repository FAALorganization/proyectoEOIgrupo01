package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import com.grupo01.java6.faal.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
public class UsuarioController {

    private final DetallesdeusuarioService detallesdeusuarioService;
    private final LoginService loginService;

    public UsuarioController(DetallesdeusuarioService detallesdeusuarioService, LoginService loginService) {
        this.detallesdeusuarioService = detallesdeusuarioService;
        this.loginService = loginService;
    }

    @GetMapping("/perfiladmin")
    public String mostrarUsuarios(Model model) {
        List<Detallesdeusuario> usuarios = detallesdeusuarioService.obtenerTodosLosUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "perfiladmin";
    }

    @PutMapping("/usuarios/desactivar/{id}")
    public ResponseEntity<String> desactivarUsuario(@PathVariable Integer id) {
        System.out.println("ID recibido: " + id); // Registro para depurar
        Login usuario = loginService.obtenerPorId(id); // Cambiar lógica para buscar por ID
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getEmailPrimario()); // Registro para depurar
            usuario.setActivo(false);
            loginService.guardarLogin(usuario);
            System.out.println("Usuario desactivado."); // Registro para depurar
            return ResponseEntity.ok("Usuario desactivado correctamente.");
        }
        System.out.println("Usuario no encontrado."); // Registro para depurar
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
    }
}