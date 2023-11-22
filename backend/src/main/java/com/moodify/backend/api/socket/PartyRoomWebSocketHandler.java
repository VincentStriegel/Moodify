package com.moodify.backend.api.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.PartyRoomTrackTO;
import com.moodify.backend.api.transferobjects.PartyRoomWebSocketTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.MessageType;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.w3c.dom.Text;

import javax.sound.midi.Track;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyRoomWebSocketHandler implements WebSocketHandler {
    private Map<String, List<WebSocketSession>> rooms = new HashMap<>();


    // create list of PartyRoomTrackTOs
    List<PartyRoomTrackTO> partyRoomTrackTOs = new ArrayList<PartyRoomTrackTO>();

    private Map<TrackTO, Integer> trackRatings = new HashMap<>();

    Set<TrackTO> trackTOSet = new LinkedHashSet<>();

    // ...

    public void addTrackWithRating(TrackTO track, Integer rating) {
        trackRatings.put(track, rating);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        rooms.computeIfAbsent(roomId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        synchronized (rooms) {
            String roomId = getRoomId(session);
            List<WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions != null && !roomSessions.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode rootNode = mapper.readTree((String) message.getPayload());
                String messageType = rootNode.get("messageType").asText();

                TextMessage responseMessage = new TextMessage("empty");

                switch (messageType) {
                    case "SUGGEST_TRACK":
                        JsonNode trackTOText = rootNode.get("trackTO");
                        TrackTO trackTO = mapper.treeToValue(trackTOText, TrackTO.class);
                        responseMessage = suggestTrack(trackTO);
                        break;
                    case "REMOVE_TRACK":
                        JsonNode removedTrackTOText = rootNode.get("trackTO");
                        TrackTO removedTrackTO = mapper.treeToValue(removedTrackTOText, TrackTO.class);
                        removeTrack(removedTrackTO);
                        sendCurrentTrack(session);
                        break;
                    case "RATE_TRACK":
                        // Handle rating a track
                        break;
                    case "GET_CURRENT_STATE":
                        // Handle getting the current state
                        break;
                    case "SET_PLAYLIST":
                        JsonNode trackTOListText = rootNode.get("trackTOList");
                        // Use TypeFactory to construct a List Type
                        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, TrackTO.class);
                        // Deserialize the JsonNode into a List<TrackTO>
                        List<TrackTO> trackTOList = mapper.readValue(trackTOListText.toString(), listType);
                        for (TrackTO trackTOEntry : trackTOList) {
                            responseMessage = suggestTrack(trackTOEntry);
                            // Handle the responseMessage if needed
                        }
                        break;
                }

/*
                TrackTO trackTO = mapper.readValue((String) message.getPayload(), TrackTO.class);
                synchronized (trackRatings) {
                    trackRatings.merge(trackTO, 1, Integer::sum);
                    trackTOList.add(trackTO);
                }
                String trackRatingsJson = mapper.writeValueAsString(trackTOList);
                TextMessage trackRatingsMessage = new TextMessage(trackRatingsJson);
             */
                for (WebSocketSession roomSession : roomSessions) {
                    roomSession.sendMessage(responseMessage);
                }
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

    private TextMessage suggestTrack (TrackTO trackTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        synchronized (trackRatings) {
            trackRatings.merge(trackTO, 1, Integer::sum);
            if(!trackTOSet.contains(trackTO)){
                trackTOSet.add(trackTO);
            }
        }
        String trackRatingsJson = mapper.writeValueAsString(trackTOSet);
        return new TextMessage(trackRatingsJson);
    }

    private void removeTrack(TrackTO trackTO) throws JsonProcessingException {
        synchronized (trackRatings) {
            trackRatings.merge(trackTO, 1, Integer::sum);
            if (trackTOSet.contains(trackTO)) {
                trackTOSet.remove(trackTO);
            }
        }
    }


    private void sendCurrentTrack(WebSocketSession session) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

/*
        // Create a new message object with the type and the track list
        PartyRoomWebSocketTO message = new PartyRoomWebSocketTO(MessageType.CURRENT_TRACK, trackTOSet.iterator().next());

        // Serialize the entire message object to JSON
        String messageJson = mapper.writeValueAsString(message);

        // Create a TextMessage with the JSON string
        TextMessage currentTrackMessage = new TextMessage(messageJson);

        // Send the message over the WebSocket session
        session.sendMessage(currentTrackMessage);

        */
        String trackRatingsJson = mapper.writeValueAsString(trackTOSet);
        session.sendMessage(new TextMessage(trackRatingsJson));
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
