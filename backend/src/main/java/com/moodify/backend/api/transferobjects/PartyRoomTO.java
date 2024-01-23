package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * This class represents a party room in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * The properties of the class represent the data that is transferred.
 * The currentPosition property represents the current position of the track being played in the party room.
 * The tracks property represents the set of tracks in the party room.
 */
@Getter
@Setter
public class PartyRoomTO {
    private float currentPosition;
    private Set<TrackTO> tracks;
}
