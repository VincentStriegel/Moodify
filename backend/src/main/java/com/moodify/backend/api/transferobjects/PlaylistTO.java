package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlaylistTO {

    private long id;
    private String title;

    private String picture_small;

    private String picture_medium;

    private String picture_big;

    private int number_of_songs;

    private List<TrackTO> trackTOList;

    public PlaylistTO() {
        this.trackTOList = new ArrayList<TrackTO>();
    }

}
