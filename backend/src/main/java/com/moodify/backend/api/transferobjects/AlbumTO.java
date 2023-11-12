package com.moodify.backend.api.transferobjects;

import java.util.ArrayList;
import java.util.List;

public class AlbumTO implements Cloneable {
    private int id;

    private int number_of_songs;

    private String title;

    private String cover_small;

    private String cover_big;

    private String release_date;

    private List<TrackTO> trackTOList;

    public AlbumTO() {
        this.trackTOList = new ArrayList<TrackTO>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
    }

    public int getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(int number_of_songs) {
        this.number_of_songs = number_of_songs;
    }

    public String getCover_big() {
        return cover_big;
    }

    public void setCover_big(String cover_big) {
        this.cover_big = cover_big;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<TrackTO> getTrackTOList() {
        List<TrackTO> copy = new ArrayList<TrackTO>();

        for (TrackTO trackTO: this.trackTOList) {
            copy.add(trackTO.clone());
        }

        return copy;
    }

    public void setTrackTOList(List<TrackTO> trackTOList) {
        this.trackTOList = trackTOList;
    }

    @Override
    public AlbumTO clone() {
        try {
            AlbumTO clone = (AlbumTO) super.clone();
            clone.id = this.id;
            clone.number_of_songs = this.number_of_songs;
            clone.title = this.title;
            clone.cover_small = this.cover_small;
            clone.cover_big = this.cover_big;
            clone.release_date = this.release_date;

            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //Handle the exception appropriately
            return null;
        }
    }
}
