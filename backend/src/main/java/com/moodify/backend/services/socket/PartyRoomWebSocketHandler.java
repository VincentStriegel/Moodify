package com.moodify.backend.services.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodify.backend.api.transferobjects.PartyRoomTO;
import com.moodify.backend.api.transferobjects.PlaylistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.services.database.DatabaseService;
import com.moodify.backend.services.database.util.TOAssembler;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * This class handles WebSocket connections for party rooms in the application.
 * It implements the WebSocketHandler interface, which defines methods for handling WebSocket lifecycle events.
 * The class maintains a map of party rooms, where the key is the room ID and the value is the PartyRoom object.
 * It also has a reference to a DatabaseService, which is used to interact with the database.
 * The class provides methods for handling WebSocket connections, messages, transport errors, and connection closures.
 * It also provides private methods for handling specific message types,
 * such as joining a room, suggesting a track, removing a track, rating a track, and setting the current position of the track.
 */
public class PartyRoomWebSocketHandler implements WebSocketHandler {

    private Map<String, PartyRoom> rooms = new HashMap<>();

    private final DatabaseService POSTGRES_SERVICE;

    public PartyRoomWebSocketHandler(DatabaseService postgresService) {
        POSTGRES_SERVICE = postgresService;
    }

    /**
     * This method is called after a WebSocket connection is established.
     * It retrieves the room ID from the session and checks if a party room with this ID already exists.
     * If not, it creates a new party room and adds it to the map of rooms.
     * Finally, it adds the session to the party room.
     *
     * @param session The WebSocket session that has been established.
     * @throws Exception If an error occurs while handling the connection.
     */
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

    /**
     * This method is called when a WebSocket message is received.
     * It synchronizes on the map of rooms to prevent concurrent modifications.
     * Then, it retrieves the room ID from the session and gets the list of sessions associated with this room.
     * If the list is not empty, it processes the message based on its type.
     * Finally, it sends a response message to all sessions associated with the room.
     *
     * @param session The WebSocket session that the message was received from.
     * @param message The WebSocket message that was received.
     * @throws Exception If an error occurs while handling the message.
     */
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

                        TOAssembler toAssembler = new TOAssembler();

                        PlaylistTO playlist = toAssembler.generatePlaylistTOFrom(POSTGRES_SERVICE.findPlaylistByIdFromUser(playlistID, userID));

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

    /**
     * This method is called when a WebSocket transport error occurs.
     * Currently, it does nothing.
     *
     * @param session The WebSocket session that the error occurred on.
     * @param exception The exception that was thrown.
     * @throws Exception If an error occurs while handling the exception.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * This method is called after a WebSocket connection is closed.
     * It retrieves the room ID from the session and gets the list of sessions associated with this room.
     * Then, it removes the session from the list.
     *
     * @param session The WebSocket session that was closed.
     * @param status The status of the connection closure.
     * @throws Exception If an error occurs while handling the connection closure.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

            String roomId = getRoomId(session);
            List<WebSocketSession> roomSessions = rooms.get(roomId).getRoomSessions();
            if (roomSessions != null) {
                roomSessions.remove(session);
            }
    }

    /**
     * This method indicates whether the WebSocket handler supports partial messages.
     * Currently, it returns false, indicating that partial messages are not supported.
     *
     * @return false, indicating that partial messages are not supported.
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * This method suggests a track to be played in a party room.
     * It retrieves the map of track ratings and the set of played tracks from the room.
     * Then, it checks if the track has already been played.
     * If not, it adds the track to the map of ratings with an initial rating of 0.
     * Finally, it sends the sorted list of tracks to all sessions associated with the room.
     *
     * @param trackTO The track to suggest.
     * @param session The WebSocket session that the suggestion was received from.
     * @throws JsonProcessingException If an error occurs while processing the JSON data.
     */
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

    /**
     * This method removes a track from the list of suggested tracks in a party room.
     * It retrieves the map of track ratings and the set of played tracks from the room.
     * Then, it checks if the track is in the map of ratings.
     * If so, it removes the track from the map and adds it to the set of played tracks.
     *
     * @param trackTO The track to remove.
     * @param session The WebSocket session that the removal request was received from.
     * @throws JsonProcessingException If an error occurs while processing the JSON data.
     */
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

    /**
     * This method sends the current track to be played in a party room.
     * It retrieves the map of track ratings from the room and sends the sorted list of tracks to all sessions associated with the room.
     *
     * @param session The WebSocket session that the request was received from.
     * @throws IOException If an error occurs while sending the message.
     */
    private void sendCurrentTrack(WebSocketSession session) throws IOException {
        Map<TrackTO, Integer> trackRatingsMap = rooms.get(getRoomId(session)).getTrackRatings();
        sendToAllSessions(getRoomId(session), sortTracks(trackRatingsMap));
    }

    /**
     * This method retrieves the ID of a WebSocket session.
     *
     * @param session The WebSocket session to retrieve the ID from.
     * @return The ID of the session.
     */
    private String getId(WebSocketSession session) {
        return session.getId();
    }

    /**
     * This method retrieves the room ID from a WebSocket session.
     * It gets the URI of the session and extracts the room ID from the path.
     *
     * @param session The WebSocket session to retrieve the room ID from.
     * @return The room ID, or null if the session has no URI.
     */
    private String getRoomId(WebSocketSession session) {
    URI uri = session.getUri();
    if (uri != null) {
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
    return null;
    }

    /**
     * This method sorts a map of tracks by their ratings.
     * It creates a set of tracks and adds all tracks from the map to the set.
     * The set is sorted in descending order of ratings, and in ascending order of track IDs for tracks with equal ratings.
     *
     * @param trackRatings The map of tracks and their ratings.
     * @return The sorted set of tracks.
     */
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

    /**
     * This method rates a track in a party room.
     * It retrieves the map of track ratings from the room and checks if the track is in the map.
     * If so, it updates the rating of the track and sends the sorted list of tracks to all sessions associated with the room.
     *
     * @param trackTO The track to rate.
     * @param session The WebSocket session that the rating was received from.
     * @param rating The rating to add to the track.
     * @throws IOException If an error occurs while sending the message.
     */
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

    /**
     * This method sends a list of tracks to all sessions associated with a party room.
     * It creates a party room transfer object and sets the current position and the list of tracks.
     * Then, it converts the transfer object to a JSON string and sends it as a message to all sessions.
     *
     * @param partyRoomId The ID of the party room.
     * @param tracks The list of tracks to send.
     * @throws JsonProcessingException If an error occurs while processing the JSON data.
     */
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

    /**
     * This method sets the current position of the track being played in a party room.
     *
     * @param currentPosition The current position to set.
     * @param session The WebSocket session that the position was received from.
     */
    private void setCurrentPosition(float currentPosition, WebSocketSession session) {
        rooms.get(getRoomId(session)).setCurrentPosition(currentPosition);
    }

}
