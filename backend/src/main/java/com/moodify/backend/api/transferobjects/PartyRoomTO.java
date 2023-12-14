package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PartyRoomTO {
    private float currentPosition;
    private Set<TrackTO> tracks;
}
