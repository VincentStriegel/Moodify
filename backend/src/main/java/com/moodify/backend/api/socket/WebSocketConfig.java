package com.moodify.backend.api.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//Websocket Configuration
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(partyRoomWebSocketHandler(), "/party-room/{roomId}").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler partyRoomWebSocketHandler() {
        return new PartyRoomWebSocketHandler();
    }
}


