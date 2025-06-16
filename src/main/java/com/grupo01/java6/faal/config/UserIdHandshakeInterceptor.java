package com.grupo01.java6.faal.config;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

public class UserIdHandshakeInterceptor implements HandshakeInterceptor {

//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
//        // Obtener userId desde parámetros o cabeceras (aquí simulado)
//        String userId = request.getURI().getQuery().split("=")[1]; // si usas ?userId=5
//
//        attributes.put("user", new StompPrincipal(userId));
//        return true;
//    }
@Override
public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Map<String, Object> attributes) {
    String userId = null;
    // Intenta primero por header
    if (request.getHeaders().containsKey("usuario")) {
        userId = request.getHeaders().getFirst("usuario"); // DEBE SER EL ID
    }
    // Si no, intenta por query param
    if (userId == null && request.getURI().getQuery() != null) {
        for (String param : request.getURI().getQuery().split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2 && pair[0].equals("userId")) {
                userId = pair[1];
            }
        }
    }
    if (userId != null) {
        attributes.put("user", new StompPrincipal(userId)); // userId es el ID como string
    }
    System.out.println("HandshakeInterceptor: usuario conectado = " + userId);
    return true;
}



    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }

    // Clase auxiliar
    static class StompPrincipal implements Principal {
        private final String name;

        public StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}

