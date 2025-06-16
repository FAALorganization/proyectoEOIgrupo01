package com.grupo01.java6.faal.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomPrincipalHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        // Usa el Principal que pusiste en los atributos en el HandshakeInterceptor
        Principal user = (Principal) attributes.get("user");
        System.out.println("HandshakeHandler: Principal asociado = " + (user != null ? user.getName() : "null"));

        if (user != null) {
            return user;
        }
        // Si no hay, usa el de la sesión HTTP (por compatibilidad)
        return super.determineUser(request, wsHandler, attributes);
    }
}

