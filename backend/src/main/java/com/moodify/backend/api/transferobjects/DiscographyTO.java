package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiscographyTO {

    private List<TrackTO>  singles;

    public DiscographyTO() {
        this.singles = new ArrayList<TrackTO>();
    }
}
