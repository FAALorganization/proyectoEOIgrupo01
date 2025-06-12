package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.MensajeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    public Mensaje guardarMensaje(Mensaje mensaje) {
        mensaje.setFechaEnvio(LocalDateTime.now());
        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajesPrivados(Login emisor, Login receptor) {
        List<Mensaje> enviados = mensajeRepository.findByEmisorAndReceptorOrderByFechaEnvioAsc(emisor, receptor);
        List<Mensaje> recibidos = mensajeRepository.findByEmisorAndReceptorOrderByFechaEnvioAsc(receptor, emisor);

        List<Mensaje> todos = new ArrayList<>();
        todos.addAll(enviados);
        todos.addAll(recibidos);
        todos.sort((m1, m2) -> m1.getFechaEnvio().compareTo(m2.getFechaEnvio()));

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

}
