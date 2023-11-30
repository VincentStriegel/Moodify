package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.domain.services.database.databaseobjects.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectTransformer {
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

    public TrackTO generateTrackTOFrom(TrackDO trackDO) {
        TrackTO trackTO =  new TrackTO();
        trackTO.setId(trackDO.getId_deezer());
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

    public ArtistTO generateArtistTOFrom(ArtistDO artistDO) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(artistDO.getArtist_id_deezer());
        //artistTO.setDeezerId(artistDO.getArtist_id_deezer());
        artistTO.setNb_fans(artistDO.getNb_fans());
        artistTO.setName(artistDO.getName());
        artistTO.setPicture_small(artistDO.getPicture_small());
        artistTO.setPicture_big(artistDO.getPicture_big());
        return artistTO;
    }

    public AlbumTO generateAlbumTOFrom(AlbumDO albumDO) {
        AlbumTO albumTO = new AlbumTO();
        albumTO.setId(albumDO.getAlbum_id_deezer());
        albumTO.setTitle(albumDO.getTitle());
        albumTO.setCover_small(albumDO.getCover_small());
        albumTO.setCover_big(albumDO.getCover_big());
        albumTO.setRelease_date(albumDO.getRelease_date());
        albumTO.setNumber_of_songs(albumDO.getNumber_of_songs());
        return albumTO;
    }

    public PersonalLibraryTO generatePersonalLibraryTOFrom(PersonalLibraryDO personalLibraryDO) {
        PersonalLibraryTO personalLibraryTO = new PersonalLibraryTO();
        personalLibraryTO.setLikedAlbums(generateAlbumTOListFrom(personalLibraryDO.getLikedAlbums()));
        personalLibraryTO.setLikedArtists(generateArtistTOListFrom(personalLibraryDO.getLikedArtists()));
        personalLibraryTO.setLikedTracks(generateTrackTOListFrom(personalLibraryDO.getPlaylists().get(0).getTracks()));
        personalLibraryTO.setCustomPlaylists(generatePlaylistTOListFrom(personalLibraryDO.getPlaylists()));
        return personalLibraryTO;

    }

    public UserTO generateUserTOFrom(UserDO userDO) {
        UserTO userTO = new UserTO();
        userTO.setId(userDO.getId());
        userTO.setUsername(userDO.getUsername());
        userTO.setPersonalLibrary(generatePersonalLibraryTOFrom(userDO.getPersonalLibrary()));
        return userTO;
    }

    private List<AlbumTO> generateAlbumTOListFrom(List<AlbumDO> albumDOList) {
        List<AlbumTO> albumTOList = new ArrayList<>();
        for (AlbumDO albumDO : albumDOList) {
            albumTOList.add(generateAlbumTOFrom(albumDO));
        }
        return albumTOList;
    }

    private List<ArtistTO> generateArtistTOListFrom(List<ArtistDO> artistDOList) {
        List<ArtistTO> artistTOList = new ArrayList<>();
        for (ArtistDO artistDO : artistDOList) {
            artistTOList.add(generateArtistTOFrom(artistDO));
        }
        return artistTOList;
    }

    private List<TrackTO> generateTrackTOListFrom(List<TrackDO> trackDOList) {
        List<TrackTO> trackTOList = new ArrayList<>();
        for (TrackDO trackDO : trackDOList) {
            trackTOList.add(generateTrackTOFrom(trackDO));
        }
        return trackTOList;
    }

    private List<PlaylistTO> generatePlaylistTOListFrom(List<PlaylistDO> playlistDOList) {
        List<PlaylistTO> playlistTOList = new ArrayList<>();
        for (PlaylistDO playlistDO : playlistDOList) {
            playlistTOList.add(generatePlaylistTOFrom(playlistDO));
        }
        return playlistTOList;
    }

    private PlaylistTO generatePlaylistTOFrom(PlaylistDO playlistDO) {
        PlaylistTO playlistTO = new PlaylistTO();
        playlistTO.setId(playlistDO.getId());
        playlistTO.setTitle(playlistDO.getTitle());
        playlistTO.setTrackTOList(generateTrackTOListFrom(playlistDO.getTracks()));
        return playlistTO;
    }
}
