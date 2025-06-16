package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CheckinDTO;
import com.grupo01.java6.faal.entities.Checkin;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.CheckinService;
import com.grupo01.java6.faal.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;

@Slf4j
@Controller
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private LoginService loginService;

    private Login getUsuarioAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null || username.equals("anonymousUser")) {
            return null;
        }
        return loginService.obtainUser(username);
    }

    @GetMapping("/checkin")
    public String listarCheckins(Model model) {
        Login login = getUsuarioAutenticado();
        if (login == null) {
            return "redirect:/login";
        }

        List<CheckinDTO> checkinsDTO = checkinService.obtenerCheckinsPorUsuario(login);
        model.addAttribute("checkins", checkinsDTO);

        boolean tieneRolJefe = loginService.tieneRolJefe(login);
        model.addAttribute("tieneRolJefe", tieneRolJefe);

        if (tieneRolJefe) {
            Login loginConSubordinados = loginService.obtenerPorIdConSubordinados(login.getId());
            if (loginConSubordinados != null) {
                List<Login> subordinadosSinJefe = loginConSubordinados.getSubordinados()
                        .stream()
                        .filter(sub -> !sub.getId().equals(login.getId()))
                        .collect(Collectors.toList());

                model.addAttribute("subordinados", subordinadosSinJefe);
            }
        }

        return "checkin"; // o el nombre de tu vista
    }



    @PostMapping("/checkin")
    public ResponseEntity<String> realizarCheckin(@RequestParam String tipo) {
        Login login = getUsuarioAutenticado();
        if (login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
        }

        try {
            String mensaje = checkinService.registrarCheckin(login, tipo);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de check-in inválido.");
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> realizarCheckout() {
        Login login = getUsuarioAutenticado();
        if (login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
        }

        String mensaje = checkinService.registrarCheckout(login);
        if (mensaje.startsWith("No se encontró")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }

        return ResponseEntity.ok(mensaje);
    }
    @GetMapping("/api/checkins/{idEmpleado}")
    @ResponseBody
    public List<CheckinDTO> obtenerCheckinsPorEmpleado(@PathVariable Integer idEmpleado) {
        Login empleado = loginService.obtenerPorId(idEmpleado);
        if (empleado == null) {
            return List.of();
        }
        return checkinService.obtenerCheckinsPorUsuario(empleado);
    }

}
