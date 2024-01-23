package com.moodify.backend.api.transferobjects;


import com.moodify.backend.api.util.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * This class represents a track in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the artist and album properties with new instances of ArtistTO and AlbumTO respectively.
 * The properties of the class represent the data that is transferred.
 * The id property represents the unique identifier of the track.
 * The title property represents the title of the track.
 * The duration property represents the duration of the track in seconds.
 * The preview property represents the URL of the preview of the track.
 * The release_date property represents the release date of the track.
 * The cover_small property represents the URL of the small cover of the track.
 * The cover_big property represents the URL of the big cover of the track.
 * The artist property represents the artist of the track.
 * The album property represents the album of the track.
 * The source property represents the source of the track data.
 * The class also overrides the toString, clone, equals, and hashCode methods from the Object class.
 */
@Getter
@Setter
public class TrackTO implements Cloneable {

    private long id;

    private String title;

    private int duration;

    private String preview;

    private String release_date;

    private String cover_small;

    private String cover_big;

    private ArtistTO artist;

    private AlbumTO album;

    private Source source;

    public TrackTO() {
        this.artist = new ArtistTO();
        this.album = new AlbumTO();
    }
    @Override
    public String toString() {
        return "TrackTO :{"
                + "id:" + id
                + ", title:'" + title + '\''
                + ", duration:" + duration
                + ", preview:'" + preview + '\''
                + ", release_date:'" + release_date + '\''
                + ", artist:" + artist
                + ", album:" + album
                + '}';
    }

    @Override
    public TrackTO clone() {
        try {
            TrackTO clone = (TrackTO) super.clone();
            clone.id = this.id;
            clone.title = this.title;
            clone.duration = this.duration;
            clone.preview = this.preview;
            clone.release_date = this.release_date;
            clone.artist = this.artist;
            clone.album = this.album;

            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrackTO trackTO = (TrackTO) o;
        return Objects.equals(id, trackTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
