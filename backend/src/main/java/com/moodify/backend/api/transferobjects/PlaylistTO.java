package com.moodify.backend.api.transferobjects;

import java.util.ArrayList;
import java.util.List;

public class PlaylistTO implements Cloneable {

    public PlaylistTO() {
        this.trackTOList = new ArrayList<TrackTO>();
    }

    private int id;
    private String title;

    private String picture_small;

    private String picture_medium;

    private String picture_big;

    private int number_of_songs;

    private List<TrackTO> trackTOList;

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

    public String getPicture_small() {
        return picture_small;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    public int getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(int number_of_songs) {
        this.number_of_songs = number_of_songs;
    }

    public List<TrackTO> getTrack_list() {
        List<TrackTO> copy = new ArrayList<TrackTO>();
        for (TrackTO trackTO: this.trackTOList) {
            copy.add(trackTO.clone());
        }
        return copy;
    }

    public void setTrackTOList(List<TrackTO> track_list) {
        this.trackTOList = track_list;
    }

    @Override
    public PlaylistTO clone() {
        try {
            PlaylistTO clone = (PlaylistTO) super.clone();
            clone.id = this.id;
            clone.title = this.title;
            clone.picture_small = this.picture_small;
            clone.picture_medium = this.picture_medium;
            clone.picture_big = this.picture_big;
            clone.number_of_songs = this.number_of_songs;
            clone.trackTOList = new ArrayList<TrackTO>();
            for (TrackTO trackTO: this.trackTOList) {
                clone.trackTOList.add(trackTO.clone());
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }
}
