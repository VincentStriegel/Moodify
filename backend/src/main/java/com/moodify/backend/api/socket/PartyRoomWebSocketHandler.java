package com.moodify.backend.api.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.PartyRoomTO;
import com.moodify.backend.api.transferobjects.PlaylistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.DatabaseService;
import com.moodify.backend.domain.services.database.ObjectTransformer;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.URI;
import java.util.*;

public class PartyRoomWebSocketHandler implements WebSocketHandler {

    private Map<String, PartyRoom> rooms = new HashMap<>();

    private final DatabaseService POSTGRES_SERVICE;

    public PartyRoomWebSocketHandler(DatabaseService postgresService) {
        POSTGRES_SERVICE = postgresService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        PartyRoom partyRoom = rooms.get(roomId);
        if (partyRoom == null) {
            partyRoom = new PartyRoom();
            rooms.put(roomId, partyRoom);
        }
        partyRoom.addSession(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        synchronized (rooms) {
            String roomId = getRoomId(session);
            List<WebSocketSession> roomSessions = rooms.get(roomId).getRoomSessions();
            if (roomSessions != null && !roomSessions.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode rootNode = mapper.readTree((String) message.getPayload());
                String messageType = rootNode.get("messageType").asText();

                TextMessage responseMessage = new TextMessage("empty");

                switch (messageType) {
                    case "JOIN_ROOM":
                        sendCurrentTrack(session);
                        break;
                    case "SUGGEST_TRACK":
                        JsonNode trackTOText = rootNode.get("trackTO");
                        TrackTO trackTO = mapper.treeToValue(trackTOText, TrackTO.class);
                        suggestTrack(trackTO, session);
                        break;
                    case "REMOVE_TRACK":
                        JsonNode removedTrackTOText = rootNode.get("trackTO");
                        TrackTO removedTrackTO = mapper.treeToValue(removedTrackTOText, TrackTO.class);
                        removeTrack(removedTrackTO, session);
                        sendCurrentTrack(session);
                        break;
                    case "RATE_TRACK":
                        JsonNode voteTrackTOText = rootNode.get("trackTO");
                        int rating = rootNode.get("rating").asInt();
                        rateTrack(mapper.treeToValue(voteTrackTOText, TrackTO.class), session, rating);
                        break;
                    case "SET_CURRENT_POSITION":
                        int position = rootNode.get("currentPosition").asInt();
                        setCurrentPosition(position, session);
                        break;
                    case "SET_PLAYLIST_ID":
                        long playlistID = rootNode.get("playlistId").asLong();
                        long userID = rootNode.get("userId").asLong();

                        ObjectTransformer objectTransformer = new ObjectTransformer();

                        PlaylistTO playlist = objectTransformer.generatePlaylistTOFrom(POSTGRES_SERVICE.findPlaylistById(playlistID, userID));

                        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, TrackTO.class);
                        // Deserialize the JsonNode into a List<TrackTO>

                        List<TrackTO> trackTOList = playlist.getTrackTOList();
                        for (TrackTO trackTOEntry : trackTOList) {
                            suggestTrack(trackTOEntry, session);
                            // Handle the responseMessage if needed
                        }
                        break;

                    default:
                        break;
                }

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
            List<WebSocketSession> roomSessions = rooms.get(roomId).getRoomSessions();
            if (roomSessions != null) {
                roomSessions.remove(session);
            }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void suggestTrack(TrackTO trackTO, WebSocketSession session) throws JsonProcessingException {
        Map<TrackTO, Integer> trackRatingsMap = rooms.get(getRoomId(session)).getTrackRatings();
        Set<TrackTO> playedTracks = rooms.get(getRoomId(session)).getPlayedTracks();
        synchronized (trackRatingsMap) {
            if (!playedTracks.contains(trackTO)) {
                trackRatingsMap.merge(trackTO, 0, Integer::sum);
            }
        }
        sendToAllSessions(getRoomId(session), sortTracks(trackRatingsMap));
    }

    private void removeTrack(TrackTO trackTO, WebSocketSession session) throws JsonProcessingException {
        Map<TrackTO, Integer> trackRatingsMap = rooms.get(getRoomId(session)).getTrackRatings();
        Set<TrackTO> playedTracks = rooms.get(getRoomId(session)).getPlayedTracks();
        synchronized (trackRatingsMap) {
            if (trackRatingsMap.containsKey(trackTO)) {
                trackRatingsMap.remove(trackTO);
                playedTracks.add(trackTO);
            }
        }
    }

    private void sendCurrentTrack(WebSocketSession session) throws IOException {
        Map<TrackTO, Integer> trackRatingsMap = rooms.get(getRoomId(session)).getTrackRatings();
        sendToAllSessions(getRoomId(session), sortTracks(trackRatingsMap));
    }

    private String getId(WebSocketSession session) {
        return session.getId();
    }

    private String getRoomId(WebSocketSession session) {
    URI uri = session.getUri();
    if (uri != null) {
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
    return null;
    }

    private Set<TrackTO> sortTracks(Map<TrackTO, Integer> trackRatings) {
        Set<TrackTO> trackTOSet = new TreeSet<>((track1, track2) -> {
            int compare = trackRatings.get(track2).compareTo(trackRatings.get(track1));
            if (compare != 0) {
                return compare;
            } else {
                return Long.compare(track1.getId(), track2.getId());
            }
        });
        trackTOSet.addAll(trackRatings.keySet());
        return trackTOSet;
    }

    private void rateTrack(TrackTO trackTO, WebSocketSession session, int rating) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<TrackTO, Integer> trackRatingsMap = rooms.get(getRoomId(session)).getTrackRatings();
        synchronized (trackRatingsMap) {
            if (trackRatingsMap.containsKey(trackTO)) {
               int currentTrackRating = trackRatingsMap.get(trackTO);
               trackRatingsMap.put(trackTO, currentTrackRating + rating);
               sendToAllSessions(getRoomId(session), sortTracks(trackRatingsMap));
            }
        }
    }

    private void sendToAllSessions(String partyRoomId, Set<TrackTO> tracks) throws JsonProcessingException {
        List<WebSocketSession> roomSessions = rooms.get(partyRoomId).getRoomSessions();

        ObjectMapper mapper = new ObjectMapper();
        PartyRoomTO partyRoomTO = new PartyRoomTO();
        partyRoomTO.setCurrentPosition(rooms.get(partyRoomId).getCurrentPosition());
        partyRoomTO.setTracks(tracks);
        String messageString = mapper.writeValueAsString(partyRoomTO);
        TextMessage message = new TextMessage(messageString);
        for (WebSocketSession roomSession : roomSessions) {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setCurrentPosition(float currentPosition, WebSocketSession session) {
        rooms.get(getRoomId(session)).setCurrentPosition(currentPosition);
    }

}
