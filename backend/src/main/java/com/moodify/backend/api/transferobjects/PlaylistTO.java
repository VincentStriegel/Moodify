package com.moodify.backend.api.transferobjects;

import com.moodify.backend.api.util.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a playlist in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the trackTOList property with an empty list.
 * The properties of the class represent the data that is transferred.
 * The id property represents the unique identifier of the playlist.
 * The title property represents the title of the playlist.
 * The picture_small property represents the URL of the small picture of the playlist.
 * The picture_medium property represents the URL of the medium-sized picture of the playlist.
 * The picture_big property represents the URL of the big picture of the playlist.
 * The number_of_songs property represents the number of songs in the playlist.
 * The trackTOList property represents the list of tracks in the playlist.
 * The source property represents the source of the playlist data.
 */
@Getter
@Setter
public class PlaylistTO implements Sourceable {

    private long id;
    private String title;

    private String picture_small;

    private String picture_medium;

    private String picture_big;

    private int number_of_songs;

    private List<TrackTO> trackTOList;

    private Source source;

    public PlaylistTO() {
        this.trackTOList = new ArrayList<TrackTO>();
    }


}
