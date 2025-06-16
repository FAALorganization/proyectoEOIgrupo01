package com.grupo01.java6.faal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import java.security.Principal;

// WebSocketConfig.java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new UserIdHandshakeInterceptor())
                .setHandshakeHandler(new CustomPrincipalHandshakeHandler()) // <-- ¡AQUÍ!
                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");

        // registry.enableSimpleBroker("/topic"); // Antes solo tenía /topic
        registry.enableSimpleBroker("/topic", "/queue", "/user"); // Ahora también /queue y /user
        registry.setUserDestinationPrefix("/user"); // Prefijo para destinos de usuario
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }



}


