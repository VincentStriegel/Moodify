package com.moodify.backend.api.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyRoomWebSocketHandler implements WebSocketHandler {
    private Map<String, List<WebSocketSession>> rooms = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        rooms.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String roomId = getRoomId(session);
        List<WebSocketSession> roomSessions = rooms.get(roomId);
        if (roomSessions != null) {
            for (WebSocketSession roomSession : roomSessions) {
                roomSession.sendMessage(message);
            }
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

            String roomId = getRoomId(session);
            List<WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions != null) {
                roomSessions.remove(session);
            }

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getId(WebSocketSession session) {
        return session.getId();
    }

    private String getRoomId(WebSocketSession session) {
    URI uri = session.getUri();
    if (uri != null) {
        String path = uri.getPath();
        Pattern pattern = Pattern.compile(".*/(\\d+)$");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
    }
    return null;
    }

}
