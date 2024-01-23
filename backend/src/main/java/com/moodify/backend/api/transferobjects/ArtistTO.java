package com.moodify.backend.api.transferobjects;

import com.moodify.backend.api.util.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an artist in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the trackTOList and albumTOList properties with empty lists.
 * The properties of the class represent the data that is transferred.
 * The id property represents the unique identifier of the artist.
 * The nb_fans property represents the number of fans of the artist.
 * The name property represents the name of the artist.
 * The picture_small property represents the URL of the small picture of the artist.
 * The picture_big property represents the URL of the big picture of the artist.
 * The trackTOList property represents the list of tracks by the artist.
 * The albumTOList property represents the list of albums by the artist.
 * The source property represents the source of the artist data.
 */
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
