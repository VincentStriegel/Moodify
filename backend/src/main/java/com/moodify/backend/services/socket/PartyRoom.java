package com.moodify.backend.services.socket;

import com.moodify.backend.api.transferobjects.TrackTO;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

public class PartyRoom {

    private float currentPosition = 0;

    private List<WebSocketSession> roomSessions = new ArrayList<>();

    private Map<TrackTO, Integer> trackRatings = new HashMap<>();

    private Set<TrackTO> playedTracks = new HashSet<>();

    public float getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(float currentPosition) {
        this.currentPosition = currentPosition;
    }

    public List<WebSocketSession> getRoomSessions() {
        return roomSessions;
    }

    public void setRoomSessions(List<WebSocketSession> roomSessions) {
        this.roomSessions = roomSessions;
    }

    public void addSession(WebSocketSession session) {
        roomSessions.add(session);
    }

    public Map<TrackTO, Integer> getTrackRatings() {
        return trackRatings;
    }

    public void setTrackRatings(Map<TrackTO, Integer> trackRatings) {
        this.trackRatings = trackRatings;
    }

    public Set<TrackTO> getPlayedTracks() {
        return playedTracks;
    }

    public void setPlayedTracks(Set<TrackTO> playedTracks) {
        this.playedTracks = playedTracks;
    }
}
