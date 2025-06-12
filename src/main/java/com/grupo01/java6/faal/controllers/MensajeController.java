package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/enviar")
    public Mensaje enviarMensaje(@RequestBody Mensaje mensaje, @AuthenticationPrincipal Login emisor) {
        mensaje.setEmisor(emisor);
        return mensajeService.guardarMensaje(mensaje);
    }

    @GetMapping("/privado/{idReceptor}")
    public List<Mensaje> getMensajesPrivados(@AuthenticationPrincipal Login emisor,
                                             @PathVariable Integer idReceptor) {
        Login receptor = loginService.getUserById(idReceptor);
        return mensajeService.obtenerMensajesPrivados(emisor, receptor);
    }

    @GetMapping("/grupal")
    public List<Mensaje> getMensajesGrupales() {
        return mensajeService.obtenerMensajesGrupales();
    }
}
