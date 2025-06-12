package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.MensajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private final LoginService loginService;
    private final MensajeService mensajeService;

    public ChatController(LoginService loginService, MensajeService mensajeService) {
        this.loginService = loginService;
        this.mensajeService = mensajeService;
    }

    @GetMapping("/chat")
    public String mostrarChat(Model model, Principal principal) {
        System.out.println(principal.getName() + " ha accedido al chat");
        Login usuario = loginService.getUserByEmail(principal.getName());
        List<Login> otrosUsuarios = loginService.obtenerTodosMenosActual(usuario.getId());
        System.out.println(usuario.getId() + " ha accedido al chat");
        List<Mensaje> ultimosMensajes = mensajeService.obtenerUltimosMensajesPorConversacion(usuario);

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("usuarios", otrosUsuarios);
        model.addAttribute("mensajesRecientes", ultimosMensajes);

        return "chat";
    }

}