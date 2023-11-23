package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackTO implements Cloneable {

    private long id;

    private String title;

    private int duration;

    private String preview;

    private String release_date;

    private ArtistTO artist;

    private AlbumTO album;

    public TrackTO() {
        this.artist = new ArtistTO();
        this.album = new AlbumTO();
    }
    @Override
    public String toString() {
        return "TrackTO{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", duration=" + duration
                + ", preview='" + preview + '\''
                + ", release_date='" + release_date + '\''
                + ", artist=" + artist
                + ", album=" + album
                + '}';
    }

}
