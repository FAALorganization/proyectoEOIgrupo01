package com.grupo01.java6.faal.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String username = accessor.getFirstNativeHeader("usuario");
//            if (username != null && !username.isEmpty()) {
//                accessor.setUser(new UserIdHandshakeInterceptor.StompPrincipal(username));
//            }
//        }


        return message;
    }
}

