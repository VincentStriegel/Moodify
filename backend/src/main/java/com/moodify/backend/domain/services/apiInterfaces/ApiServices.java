package com.moodify.backend.domain.services.apiInterfaces;

import com.moodify.backend.api.transferobjects.TrackTO;

import java.util.List;

public interface ApiServices {
    TrackTO getTrack(int id);
    List<TrackTO> getTracks(String name);
}
