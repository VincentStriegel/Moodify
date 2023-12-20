package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.databaseobjects.AlbumDO;
import com.moodify.backend.domain.services.database.databaseobjects.ArtistDO;
import com.moodify.backend.domain.services.database.databaseobjects.TrackDO;
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

        trackDO.setId_deezer(deezerId);
        trackDO.setTitle(title);
        trackDO.setDuration(duration);
        trackDO.setPreview(preview);
        trackDO.setRelease_date(releaseDate);
        trackDO.setArtist_id_deezer(artistIdDeezer);
        trackDO.setArtist_name_deezer(artistNameDeezer);
        trackDO.setAlbum_cover_big_deezer(albumBigCover);
        trackDO.setAlbum_cover_small_deezer(albumSmallCover);

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
