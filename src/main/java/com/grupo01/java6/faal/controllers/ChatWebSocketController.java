package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.Mensaje2DTO;
import com.grupo01.java6.faal.services.MensajeService;
import com.grupo01.java6.faal.dto.MensajeDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private LoginService loginService;

    @MessageMapping("/enviarMensaje")
    public void enviarMensaje(Mensaje2DTO dto, Principal principal) {
        System.out.println("Principal conectado: " + (principal != null ? principal.getName() : "null"));
        System.out.println("Enviando a receptor: " + dto.getIdReceptor());
        System.out.println("Enviando a emisor: " + dto.getIdEmisor());
        System.out.println("🟢 DTO RECIBIDO: " + dto);
        System.out.println("Contenido: " + dto.getContenido());
        System.out.println("Emisor ID: " + dto.getIdEmisor());
        System.out.println("Receptor ID: " + dto.getIdReceptor());
        System.out.println("Recibido DTO: emisorId=" + dto.getIdEmisor() + ", receptorId=" + dto.getIdReceptor());

        if (dto.getIdEmisor() == null) {
            throw new IllegalArgumentException("El emisorId no puede ser null");
        }

        Login emisor = loginService.getUserById(dto.getIdEmisor());

        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(dto.getContenido());
        mensaje.setEmisor(emisor);
        mensaje.setEsGrupal(dto.getEsGrupal());

        if (!dto.getEsGrupal() && dto.getIdReceptor() != null) {
            // Chat privado
            Login receptor = loginService.getUserById(dto.getIdReceptor());
            mensaje.setReceptor(receptor);

            System.out.println("GUARDANDO mensaje: " + mensaje);

            Mensaje saved = mensajeService.guardarYEnviarMensaje(mensaje);

            Mensaje2DTO dto2 = new Mensaje2DTO();
            dto2.setId(saved.getId());
            dto2.setContenido(saved.getContenido());
            dto2.setFechaEnvio(saved.getFechaEnvio());
            dto2.setEsGrupal(false);
            dto2.setEsLeido(saved.getEsLeido());
            dto2.setIdEmisor(saved.getEmisor().getId());
            dto2.setIdReceptor(saved.getReceptor().getId());
            // dto2.setEmisorNombre(saved.getEmisor().getNombre());

            // System.out.println("📤 Enviando por WebSocket a: /queue/mensajes/" + dto2.getIdReceptor());
            // System.out.println("Payload: " + dto2);
            // messagingTemplate.convertAndSend("/queue/mensajes/" + dto2.getIdReceptor(), dto2);
            // messagingTemplate.convertAndSend("/queue/mensajes/" + dto2.getIdEmisor(), dto2); // Echo al emisor

            System.out.println("📤 Enviando por WebSocket PRIVADO al RECEPTOR " + dto2.getIdReceptor());
            System.out.println("Payload: " + dto2);
            messagingTemplate.convertAndSendToUser(
                    String.valueOf(dto2.getIdReceptor()),
                    "/queue/mensajes",
                    dto2
            );

            System.out.println("📤 Enviando por WebSocket PRIVADO al EMISOR " + dto2.getIdEmisor());
            System.out.println("Payload: " + dto2);
            messagingTemplate.convertAndSendToUser(
                    String.valueOf(dto2.getIdEmisor()),
                    "/queue/mensajes",
                    dto2
            );


        } else {
            // Chat grupal
            System.out.println("GUARDANDO mensaje: " + mensaje);
            Mensaje saved = mensajeService.guardarYEnviarMensaje(mensaje);

            Mensaje2DTO dto2 = new Mensaje2DTO();
            dto2.setId(saved.getId());
            dto2.setContenido(saved.getContenido());
            dto2.setFechaEnvio(saved.getFechaEnvio());
            dto2.setEsGrupal(true);
            dto2.setEsLeido(saved.getEsLeido());
            dto2.setIdEmisor(saved.getEmisor().getId());
            // dto2.setEmisorNombre(saved.getEmisor().getIdDetallesDeUsuario().getNombre());

            // System.out.println("📤 Enviando por WebSocket a: /queue/mensajes/" + dto2.getIdReceptor());
            // messagingTemplate.convertAndSend("/topic/mensajes", dto2);

            System.out.println("📤 Enviando por WebSocket GRUPAL a: /topic/mensajes");
            messagingTemplate.convertAndSend("/topic/mensajes", dto2);
        }
    }

}
