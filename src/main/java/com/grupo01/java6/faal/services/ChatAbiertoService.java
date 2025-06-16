package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.entities.ChatAbierto;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.ChatAbiertoRepository;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatAbiertoService {

    private final ChatAbiertoRepository chatAbiertoRepository;
    private final LoginRepository loginRepository;
    private final DetallesdeusuarioService detallesdeusuarioService;

    public ChatAbiertoService(ChatAbiertoRepository chatAbiertoRepository, LoginRepository loginRepository, DetallesdeusuarioService detallesdeusuarioService) {
        this.chatAbiertoRepository = chatAbiertoRepository;
        this.loginRepository = loginRepository;
        this.detallesdeusuarioService = detallesdeusuarioService;
    }


    public List<List<CompaneroDTO>> obtenerListaCompanerosDTOChatsCerradosYAbiertos(Login login) {
        List<ChatAbierto> chats = chatAbiertoRepository.findChatsActivosByUsuario(login);
        List<CompaneroDTO> chatsAbiertos = new ArrayList<>();
        List<CompaneroDTO> chatsCerrados = new ArrayList<>();

        for (ChatAbierto chatAbierto : chats) {
            CompaneroDTO companeroDTO = new CompaneroDTO();
            Login loginB = chatAbierto.getUsuarioB();
            Detallesdeusuario detallesB = detallesdeusuarioService.buscarEntity(loginB.getId()).get();

            companeroDTO.setId(loginB.getId());
            companeroDTO.setNombre(detallesB.getNombre());
            companeroDTO.setApellidos(detallesB.getApellidos());

            if (chatAbierto.isActivo()) {
                chatsAbiertos.add(companeroDTO);
            } else {
                chatsCerrados.add(companeroDTO);
            }
        }

        // Creamos una lista que contenga las dos listas
        List<List<CompaneroDTO>> resultado = new ArrayList<>();
        resultado.add(chatsAbiertos);
        resultado.add(chatsCerrados);

        return resultado;
    }
}

