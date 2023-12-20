package com.moodify.backend.services.socket;

import com.moodify.backend.services.database.DatabaseService;
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
    public WebSocketConfig(DatabaseService postgresService) {
        POSTGRES_SERVICE = postgresService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(partyRoomWebSocketHandler(), "/party-room/{roomId}").setAllowedOrigins("*");
    }

    private final DatabaseService POSTGRES_SERVICE;

    @Bean
    public WebSocketHandler partyRoomWebSocketHandler() {
        return new PartyRoomWebSocketHandler(POSTGRES_SERVICE);
    }
}


