package com.moodify.backend.services.database.util;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.services.database.objects.AlbumDO;
import com.moodify.backend.services.database.objects.ArtistDO;
import com.moodify.backend.services.database.objects.TrackDO;
import org.springframework.stereotype.Component;

@Component
public class DOAssembler {

    public TrackDO generateTrackDOFrom(TrackTO trackTO) {
        long deezerId = trackTO.getId();
        String title = trackTO.getTitle();
        int duration = trackTO.getDuration();
        String preview = trackTO.getPreview();
        String releaseDate = trackTO.getRelease_date();
        long artistIdDeezer = trackTO.getArtist().getId();
        String artistNameDeezer = trackTO.getArtist().getName();
        String albumBigCover = trackTO.getAlbum().getCover_big();
        String albumSmallCover = trackTO.getAlbum().getCover_small();


        TrackDO trackDO = new TrackDO();

        trackDO.setId_source(deezerId);
        trackDO.setTitle(title);
        trackDO.setDuration(duration);
        trackDO.setPreview(preview);
        trackDO.setRelease_date(releaseDate);
        trackDO.setArtist_id_source(artistIdDeezer);
        trackDO.setArtist_name(artistNameDeezer);
        trackDO.setCover_big(albumBigCover);
        trackDO.setCover_small(albumSmallCover);

        return trackDO;
    }

    public ArtistDO generateArtistDOFrom(ArtistTO artistTO) {

        int nbOfFans = artistTO.getNb_fans();
        String name = artistTO.getName();
        String pictureSmall = artistTO.getPicture_small();
        String pictureBig = artistTO.getPicture_big();
        long artistIdDeezer = artistTO.getId();

        return new ArtistDO(nbOfFans, name, pictureSmall, pictureBig, artistIdDeezer);
    }

    public AlbumDO generateAlbumDoFrom(AlbumTO albumTO) {


        String title = albumTO.getTitle();
        String coverSmall = albumTO.getCover_small();
        String coverBig = albumTO.getCover_big();
        String releaseDate = albumTO.getRelease_date();
        int  numberOfSongs = albumTO.getNumber_of_songs();
        long albumIdDeezer = albumTO.getId();

        return new AlbumDO(title, coverSmall, coverBig, releaseDate, numberOfSongs, albumIdDeezer);
    }
}
