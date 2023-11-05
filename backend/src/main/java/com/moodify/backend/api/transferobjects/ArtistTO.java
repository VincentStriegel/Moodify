package com.moodify.backend.api.transferobjects;

import java.util.ArrayList;
import java.util.List;

public class ArtistTO implements Cloneable {
    private int id;

    private String name;

    private String picture_small;

    private String picture_big;

    private List<TrackTO> trackTOList;

    private List<AlbumTO> albumTOList;


    public ArtistTO() {
        this.trackTOList = new ArrayList<TrackTO>();
        this.albumTOList = new ArrayList<AlbumTO>();

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

    public List<AlbumTO> getAlbumTOList() {
        List<AlbumTO> copy = new ArrayList<AlbumTO>();
        for (AlbumTO albumTO: this.albumTOList) {
            copy.add(albumTO.clone());
        }
        return copy;
    }

    public void setAlbumTOList(List<AlbumTO> albumTOList) {
        this.albumTOList = albumTOList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture_small() {
        return picture_small;
    }

    public void setPicture_small(String picture_small) {
        this.picture_small = picture_small;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

    @Override
    public ArtistTO clone() {
        try {
            ArtistTO clone = (ArtistTO) super.clone();
            clone.id = this.id;
            clone.name = this.name;
            clone.picture_small = this.picture_small;
            clone.picture_big = this.picture_big;

            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }
}
