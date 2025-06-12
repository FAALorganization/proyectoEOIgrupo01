package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dto.MensajeDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private LoginService loginService;

    @MessageMapping("/enviarMensaje")
    public void enviarMensaje(MensajeDTO dto) {
        Login emisor = loginService.getUserById(dto.getEmisorId());

        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(dto.getContenido());
        mensaje.setEmisor(emisor);
        mensaje.setEsGrupal(dto.getEsGrupal());

        if (!dto.getEsGrupal() && dto.getReceptorId() != null) {
            Login receptor = loginService.getUserById(dto.getReceptorId());
            mensaje.setReceptor(receptor);

            // Guardar y enviar al receptor
            Mensaje saved = mensajeService.guardarMensaje(mensaje);
            messagingTemplate.convertAndSend("/queue/mensajes/" + dto.getReceptorId(), saved);
            messagingTemplate.convertAndSend("/queue/mensajes/" + dto.getEmisorId(), saved); // Eco al emisor
        } else {
            // Chat grupal
            Mensaje saved = mensajeService.guardarMensaje(mensaje);
            messagingTemplate.convertAndSend("/topic/mensajes", saved);
        }
    }
}