package com.moodify.backend.api.transferobjects;

import com.moodify.backend.api.util.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an album in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the trackTOList property with an empty list.
 * The properties of the class represent the data that is transferred.
 * The id property represents the unique identifier of the album.
 * The number_of_songs property represents the number of songs in the album.
 * The title property represents the title of the album.
 * The cover_small property represents the URL of the small cover image of the album.
 * The cover_big property represents the URL of the big cover image of the album.
 * The release_date property represents the release date of the album.
 * The trackTOList property represents the list of tracks in the album.
 * The source property represents the source of the album data.
 */
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

    private Source source;

    public AlbumTO() {
        this.trackTOList = new ArrayList<TrackTO>();
    }


}
