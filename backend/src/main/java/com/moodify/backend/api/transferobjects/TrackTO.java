package com.moodify.backend.api.transferobjects;

public class TrackTO implements Cloneable {

    private long id;

    private String title;

    private int duration;

    private String preview;

    private String release_date;

    private ArtistTO artist;

    private AlbumTO album;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public ArtistTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistTO artist) {
        this.artist = artist;
    }

    public AlbumTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumTO album) {
        this.album = album;
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
}
