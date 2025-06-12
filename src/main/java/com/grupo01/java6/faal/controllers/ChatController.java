package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import com.grupo01.java6.faal.services.MensajeService;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private final LoginService loginService;
    private final MensajeService mensajeService;
    private final DetallesdeusuarioService detallesdeusuarioService;

    public ChatController(LoginService loginService, MensajeService mensajeService, DetallesdeusuarioService detallesdeusuarioService) {
        this.loginService = loginService;
        this.mensajeService = mensajeService;
        this.detallesdeusuarioService = detallesdeusuarioService;
    }

    @GetMapping("/chat")
    public String mostrarChat(Model model, Principal principal) {
        System.out.println(principal.getName() + " ha accedido al chat");
        Login usuario = loginService.getUserByEmail(principal.getName());
        List<Login> otrosUsuarios = loginService.obtenerTodosMenosActual(usuario.getId());
        List<Detallesdeusuario> detalles = detallesdeusuarioService.obtenerTodosLosUsuarios();
        System.out.println(usuario.getId() + " ha accedido al chat");
        List<Mensaje> ultimosMensajes = mensajeService.obtenerUltimosMensajesPorConversacion(usuario);

        List<CompaneroDTO> usuariosDTO = new ArrayList<>();
        for (Detallesdeusuario user : detalles) {
            if (usuario.getId() != user.getId() && user.getId() != 2) {
                CompaneroDTO companeroDTO = new CompaneroDTO();
                companeroDTO.setId(user.getId());
                companeroDTO.setNombre(user.getNombre());
                companeroDTO.setApellidos(user.getApellidos());

                usuariosDTO.add(companeroDTO);
            }
        }

        System.out.println("Usuarios enviados al chat: " + usuariosDTO.size());

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("usuarios", usuariosDTO);
        //model.addAttribute("mensajesRecientes", ultimosMensajes);

        return "chat";
    }

}