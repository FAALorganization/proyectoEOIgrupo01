//package com.grupo01.java6.faal.services;
//
//import com.grupo01.java6.faal.dtos.Mensaje2DTO;
//import com.grupo01.java6.faal.entities.Login;
//import com.grupo01.java6.faal.entities.Mensaje;
//import com.grupo01.java6.faal.repositories.MensajeRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class MensajeService {
//
//    private final MensajeRepository mensajeRepository;
//
//    public MensajeService(MensajeRepository mensajeRepository) {
//        this.mensajeRepository = mensajeRepository;
//    }
//
//    public Mensaje guardarMensaje(Mensaje mensaje) {
//        mensaje.setFechaEnvio(LocalDateTime.now());
//        return mensajeRepository.save(mensaje);
//    }
//
//    public List<Mensaje> obtenerMensajesPrivados(Login emisor, Login receptor) {
//        List<Mensaje> enviados = mensajeRepository.findByEmisorAndReceptorOrderByFechaEnvioAsc(emisor, receptor);
//        List<Mensaje> recibidos = mensajeRepository.findByEmisorAndReceptorOrderByFechaEnvioAsc(receptor, emisor);
//
//        List<Mensaje> todos = new ArrayList<>();
//        todos.addAll(enviados);
//        todos.addAll(recibidos);
//        todos.sort((m1, m2) -> m1.getFechaEnvio().compareTo(m2.getFechaEnvio()));
//
//        return todos;
//    }
//
//    public List<Mensaje> obtenerMensajesGrupales() {
//        return mensajeRepository.findByEsGrupalTrueOrderByFechaEnvioAsc();
//    }
//
//    public List<Mensaje> obtenerUltimosMensajesPorConversacion(Login usuario) {
//        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);
//        List<Mensaje> todos = mensajeRepository.findMensajesRecientes(usuario.getId(), haceUnMes);
//        Map<String, Mensaje> ultimos = new HashMap<>();
//
//        for (Mensaje m : todos) {
//            String clave = m.getEsGrupal() ? "grupal" : (
//                    m.getEmisor().getId().equals(usuario.getId()) ?
//                            "privado_" + m.getReceptor().getId() :
//                            "privado_" + m.getEmisor().getId()
//            );
//
//            if (!ultimos.containsKey(clave) || m.getFechaEnvio().isAfter(ultimos.get(clave).getFechaEnvio())) {
//                ultimos.put(clave, m);
//            }
//        }
//
//        return new ArrayList<>(ultimos.values());
//    }
//
//    public List<Mensaje2DTO> obtenerMensajesPrivadosEntreUsuariosUltimoMes(Login usuarioA, Login usuarioB) {
//        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);
//        // Devuelve todos los mensajes entre usuarioA y usuarioB en el último mes, ordenados por fecha
//        return mensajeRepository.findMensajesPrivadosEntreUsuariosDesde(usuarioA.getId(), usuarioB.getId(), haceUnMes);
//    }
//
//}

package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.Mensaje2DTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final DetallesdeusuarioService detallesdeusuarioService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MensajeService(
            MensajeRepository mensajeRepository,
            DetallesdeusuarioService detallesdeusuarioService,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.mensajeRepository = mensajeRepository;
        this.detallesdeusuarioService = detallesdeusuarioService;
        this.messagingTemplate = messagingTemplate;
    }

    public Mensaje guardarMensaje(Mensaje mensaje) {
        mensaje.setFechaEnvio(LocalDateTime.now());
        return mensajeRepository.save(mensaje);
    }

    public Mensaje guardarYEnviarMensaje(Mensaje mensaje) {
        System.out.println("💾 Intentando guardar: " + mensaje);
        mensaje.setFechaEnvio(LocalDateTime.now());
        Mensaje saved = mensajeRepository.save(mensaje);
        System.out.println("✅ Guardado con ID: " + saved.getId());

        if (!mensaje.getEsGrupal() && mensaje.getReceptor() != null) {
            // Chat privado
            Detallesdeusuario detallesEmisor = detallesdeusuarioService.buscar(mensaje.getEmisor().getId()).orElse(null);
            Detallesdeusuario detallesReceptor = detallesdeusuarioService.buscar(mensaje.getReceptor().getId()).orElse(null);

            Map<String, Object> payload = new HashMap<>();
            payload.put("id", mensaje.getId());
            payload.put("contenido", mensaje.getContenido());
            payload.put("fechaEnvio", mensaje.getFechaEnvio().toString());
            payload.put("emisorId", mensaje.getEmisor().getId());
            payload.put("receptorId", mensaje.getReceptor().getId());
            payload.put("nombreEmisor", detallesEmisor != null ? detallesEmisor.getNombre() : "Desconocido");
            payload.put("nombreReceptor", detallesReceptor != null ? detallesReceptor.getNombre() : "Desconocido");

            messagingTemplate.convertAndSend("/topic/chat/" + mensaje.getReceptor().getId(), payload);
            messagingTemplate.convertAndSend("/topic/chat/" + mensaje.getEmisor().getId(), payload);
        } else {
            // Chat grupal
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", mensaje.getId());
            payload.put("contenido", mensaje.getContenido());
            payload.put("fechaEnvio", mensaje.getFechaEnvio().toString());
            payload.put("emisorId", mensaje.getEmisor().getId());
            payload.put("nombreEmisor", mensaje.getEmisor().getId()); // o su nombre si lo tienes

            messagingTemplate.convertAndSend("/topic/mensajes", payload);
        }

        return saved;
    }


    public List<Mensaje> obtenerMensajesPrivados(Login emisor, Login receptor) {
        List<Mensaje> enviados = mensajeRepository.findByEmisorAndReceptorOrderByFechaEnvioAsc(emisor, receptor);
        List<Mensaje> recibidos = mensajeRepository.findByEmisorAndReceptorOrderByFechaEnvioAsc(receptor, emisor);

        List<Mensaje> todos = new ArrayList<>();
        todos.addAll(enviados);
        todos.addAll(recibidos);
        todos.sort(Comparator.comparing(Mensaje::getFechaEnvio));

        return todos;
    }

    public List<Mensaje> obtenerMensajesGrupales() {
        return mensajeRepository.findByEsGrupalTrueOrderByFechaEnvioAsc();
    }

    public List<Mensaje> obtenerUltimosMensajesPorConversacion(Login usuario) {
        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);
        List<Mensaje> todos = mensajeRepository.findMensajesRecientes(usuario.getId(), haceUnMes);
        Map<String, Mensaje> ultimos = new HashMap<>();

        for (Mensaje m : todos) {
            String clave = m.getEsGrupal() ? "grupal" : (
                    m.getEmisor().getId().equals(usuario.getId()) ?
                            "privado_" + m.getReceptor().getId() :
                            "privado_" + m.getEmisor().getId()
            );

            if (!ultimos.containsKey(clave) || m.getFechaEnvio().isAfter(ultimos.get(clave).getFechaEnvio())) {
                ultimos.put(clave, m);
            }
        }

        return new ArrayList<>(ultimos.values());
    }

    public List<Mensaje2DTO> obtenerMensajesPrivadosEntreUsuariosUltimoMes(Login usuarioA, Login usuarioB) {
        LocalDateTime haceUnMes = LocalDateTime.now().minusMonths(1);
        return mensajeRepository.findMensajesPrivadosEntreUsuariosDesde(usuarioA.getId(), usuarioB.getId(), haceUnMes);
    }
}
