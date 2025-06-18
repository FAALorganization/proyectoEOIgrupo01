package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Controller
public class HomeController {


    private final LoginService loginService;
    private final DetallesdeusuarioService detallesdeusuarioService;
    private final TareaService tareaService;
    private final FrasesService frasesService;
    private final CheckinService checkinService;

    public HomeController(LoginService loginService, DetallesdeusuarioService detallesdeusuarioService, TareaService tareaService, FrasesService frasesService, CheckinService checkinService) {
        this.loginService = loginService;
        this.detallesdeusuarioService = detallesdeusuarioService;
        this.tareaService = tareaService;
        this.frasesService = frasesService;
        this.checkinService = checkinService;
    }


    @GetMapping("/home")
    public String showgestionVacaciones(Model model, Principal principal)
    {
        String correoUser = principal.getName();
        Login loginUser = loginService.obtainUser(correoUser);

        boolean checkoutPendiente = checkinService.tieneCheckoutPendiente(loginUser);
        model.addAttribute("checkoutPendiente", checkoutPendiente);

        String nombreUsuario = detallesdeusuarioService.findByEmail(correoUser).getNombre();
        Integer idUser = loginUser.getId();

        List<Tarea> tareasUsario = tareaService.obtenerTareasEstadoAndUsuario(idUser, "pendiente");
        List<Tarea> eventos = new ArrayList<>();
        List<Tarea> tareas = new ArrayList<>();

        LocalDate hoy = LocalDate.now();

        for (Tarea evento : tareasUsario) {
            LocalDate fechaInicio = evento.getFechaInicio();
            LocalDate fechaFin = evento.getFechaLimite();

            boolean estaEnRango = !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
            if (estaEnRango && evento.getTipoTarea().getId() == 7) {
                eventos.add(evento);
            } else if (estaEnRango && evento.getTipoTarea().getId() != 7) {
                tareas.add(evento);
            }
        }

        // Añadir atributos al modelo fuera del bucle, para que siempre se envíen
        model.addAttribute("name", nombreUsuario);
        model.addAttribute("eventos", eventos);
        model.addAttribute("tareas", tareas);

        Random random = new Random();
        int numeroAleatorio = random.nextInt(100) + 1; // genera entre 1 y 100
        String frase = frasesService.getFraseById(numeroAleatorio).getFrase();

        model.addAttribute("frase", frase);

        return "home";
    }

}
