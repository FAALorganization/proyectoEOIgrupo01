package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.dtos.Mensaje2DTO;
import com.grupo01.java6.faal.entities.ChatAbierto;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.ChatAbiertoService;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.MensajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<Mensaje> ultimosMensajes = mensajeService.obtenerUltimosMensajesPorConversacion(usuario);

        // Agregar al modelo
        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("usuarios", usuariosConChatAbierto);
        model.addAttribute("usuariosCerrados", usuariosConChatCerrado); // Nuevo
        model.addAttribute("mensajesRecientes", ultimosMensajes);

        System.out.println("Lista abiertos: " + usuariosConChatAbierto.size());
        for (CompaneroDTO companeroDTO : usuariosConChatAbierto) {
            System.out.println("Chat abierto con: " + companeroDTO.getNombre() + " " + companeroDTO.getApellidos());
        }
        System.out.println("Lista cerrados: " + usuariosConChatCerrado.size());
        return "chat";
    }

    @PostMapping("/chat/activar")
    @ResponseBody
    public ResponseEntity<?> activarChat(@RequestParam Integer usuarioBId, Principal principal) {
        // Obtener usuario logueado (usuarioA)
        Login usuarioA = loginService.getUserByEmail(principal.getName());

        // Activar chat entre usuarioA y usuarioB
        boolean exito = chatAbiertoService.activarChat(usuarioA.getId(), usuarioBId);

        if (exito) {
            return ResponseEntity.ok("Chat activado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat no encontrado");
        }
    }

    @PostMapping("/chat/desactivar")
    @ResponseBody
    public ResponseEntity<?> desactivarChat(@RequestParam Integer usuarioBId, Principal principal) {
        // Obtener usuario logueado (usuarioA)
        Login usuarioA = loginService.getUserByEmail(principal.getName());

        // Desactivar chat entre usuarioA y usuarioB
        boolean exito = chatAbiertoService.desactivarChat(usuarioA.getId(), usuarioBId);

        if (exito) {
            return ResponseEntity.ok("Chat desactivado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat no encontrado");
        }
    }

    @PostMapping("/chat/mensajes")
    @ResponseBody
    public ResponseEntity<List<Mensaje2DTO>> obtenerMensajes(@RequestParam Integer usuarioBId, Principal principal) {
        Login usuarioA = loginService.getUserByEmail(principal.getName());
        Login usuarioB = loginService.getUserBy_Id(usuarioBId);

        List<ChatAbierto> chatsAbiertos = chatAbiertoService.obtenerChatActivosOfUser(usuarioA);
//        System.out.println("Los usuarios que chatean son: " + usuarioA.getId() + " " + usuarioB.getId());
//        System.out.println("El size de chatsAbiertos es: " + chatsAbiertos.size());
        for (ChatAbierto chatAbierto : chatsAbiertos) {
//            System.out.println("Es igual??? ->>> " + chatAbierto.getUsuarioB().getId().equals(usuarioB.getId()));
            if (chatAbierto.getUsuarioB().getId().equals(usuarioB.getId())) {
                // Obtener todos los mensajes entre usuarioA y usuarioB del último mes
                List<Mensaje2DTO> listaMensajes = mensajeService.obtenerMensajesPrivadosEntreUsuariosUltimoMes(usuarioA, usuarioB);
                return ResponseEntity.ok(listaMensajes);
            }
        }

        // Si no hay chat abierto entre ellos, devuelve 404 o lista vacía
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }





}
