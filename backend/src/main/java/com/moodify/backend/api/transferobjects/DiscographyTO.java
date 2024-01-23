package com.moodify.backend.api.transferobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a discography in the Moodify application.
 * It is a transfer object (TO), which is used to send data across processes or across the network.
 * The class provides getter and setter methods for its properties.
 * It also provides a no-arg constructor that initializes the singles property with an empty list.
 * The properties of the class represent the data that is transferred.
 * The picture_small property represents the URL of the small picture of the discography.
 * The picture_big property represents the URL of the big picture of the discography.
 * The singles property represents the list of singles in the discography.
 */
@Getter
@Setter
public class DiscographyTO {

    private String picture_small;
    private String picture_big;

    private List<TrackTO>  singles;

    public DiscographyTO() {
        this.singles = new ArrayList<TrackTO>();
    }
}
