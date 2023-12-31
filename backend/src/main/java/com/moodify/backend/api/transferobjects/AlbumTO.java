package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AlbumTO {

    private long id;

    private int number_of_songs;

    private String title;

    private String cover_small;

    private String cover_big;

    private String release_date;

    private List<TrackTO> trackTOList;

    public AlbumTO() {
        this.trackTOList = new ArrayList<TrackTO>();
    }


}
