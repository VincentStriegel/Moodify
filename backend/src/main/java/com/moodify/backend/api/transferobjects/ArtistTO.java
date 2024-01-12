package com.moodify.backend.api.transferobjects;

import com.moodify.backend.api.util.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArtistTO {
    private long id;

    private int nb_fans;

    private String name;

    private String picture_small;

    private String picture_big;

    private List<TrackTO> trackTOList;

    private List<AlbumTO> albumTOList;

    private Source source;

    public ArtistTO() {
        this.trackTOList = new ArrayList<TrackTO>();
        this.albumTOList = new ArrayList<AlbumTO>();

    }

}
