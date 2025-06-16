package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.ChatAbiertoService;
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
    private final ChatAbiertoService chatAbiertoService;

    public ChatController(LoginService loginService, MensajeService mensajeService, ChatAbiertoService chatAbiertoService) {
        this.loginService = loginService;
        this.mensajeService = mensajeService;
        this.chatAbiertoService = chatAbiertoService;
    }

    @GetMapping("/chat")
    public String mostrarChat(Model model, Principal principal) {
        // Obtener usuario actual
        Login usuario = loginService.getUserByEmail(principal.getName());
        System.out.println(usuario.getEmailPrimario() + " ha accedido al chat");

        List<List<CompaneroDTO>> todosChats = chatAbiertoService.obtenerListaCompanerosDTOChatsCerradosYAbiertos(usuario);
        // Obtener usuarios con chat abierto
        List<CompaneroDTO> usuariosConChatAbierto = todosChats.get(0);

        // Obtener usuarios sin chat abierto (cerrados)
        List<CompaneroDTO> usuariosConChatCerrado = todosChats.get(1);

        // Obtener últimos mensajes
        //List<Mensaje> ultimosMensajes = mensajeService.obtenerUltimosMensajesPorConversacion(usuario);

        // Agregar al modelo
        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("usuarios", usuariosConChatAbierto);
        model.addAttribute("usuariosCerrados", usuariosConChatCerrado); // Nuevo
        //model.addAttribute("mensajesRecientes", ultimosMensajes);

        return "chat";
    }

}
