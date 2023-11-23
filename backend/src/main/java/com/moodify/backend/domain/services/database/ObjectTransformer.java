package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.AlbumTO;
import com.moodify.backend.api.transferobjects.ArtistTO;
import com.moodify.backend.api.transferobjects.TrackTO;
import com.moodify.backend.domain.services.database.databaseobjects.AlbumDO;
import com.moodify.backend.domain.services.database.databaseobjects.ArtistDO;
import com.moodify.backend.domain.services.database.databaseobjects.TrackDO;
import org.springframework.stereotype.Component;

@Component
public class ObjectTransformer {
    public TrackDO generateTrackDOFrom(TrackTO trackTO) {
        String title = trackTO.getTitle();
        int duration = trackTO.getDuration();
        String preview = trackTO.getPreview();
        String releaseDate = trackTO.getRelease_date();
        long artistIdDeezer = trackTO.getArtist().getId();
        String artistNameDeezer = trackTO.getArtist().getName();
        String albumBigCover = trackTO.getAlbum().getCover_big();
        String albumSmallCover = trackTO.getAlbum().getCover_small();



        TrackDO trackDO = new TrackDO();

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

    public TrackTO generateTrackTOFrom(TrackDO trackDO) {
        TrackTO trackTO =  new TrackTO();
        trackTO.setId(trackDO.getId());
        trackTO.setTitle(trackDO.getTitle());
        trackTO.setDuration(trackDO.getDuration());
        trackTO.setPreview(trackDO.getPreview());
        trackTO.setRelease_date(trackDO.getRelease_date());
        trackTO.getArtist().setId(trackDO.getArtist_id_deezer());
        trackTO.getArtist().setName(trackDO.getArtist_name_deezer());
        trackTO.getAlbum().setCover_big(trackDO.getAlbum_cover_big_deezer());
        trackTO.getAlbum().setCover_small(trackDO.getAlbum_cover_small_deezer());

        return trackTO;
    }

    public ArtistDO generateArtistDOFrom(ArtistTO artistTO) {


        int nbOfFans = artistTO.getNb_fans();
        String name = artistTO.getName();
        String pictureSmall = artistTO.getPicture_small();
        String pictureBig = artistTO.getPicture_big();
        long artistIdDeezer = artistTO.getId();

        ArtistDO artistDO = new ArtistDO(nbOfFans, name, pictureSmall, pictureBig, artistIdDeezer);
         return  artistDO;
    }

    public AlbumDO generateAlbumDoFrom(AlbumTO albumTO) {


        String title = albumTO.getTitle();
        String coverSmall = albumTO.getCover_small();
        String coverBig = albumTO.getCover_big();
        String releaseDate = albumTO.getRelease_date();
        int  numberOfSongs = albumTO.getNumber_of_songs();
        long albumIdDeezer = albumTO.getId();

        AlbumDO albumDO = new AlbumDO(title, coverSmall, coverBig, releaseDate, numberOfSongs, albumIdDeezer);

        return albumDO;
    }
}
