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
