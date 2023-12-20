package com.moodify.backend.services.database.util;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.services.database.objects.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TOAssembler {

    public List<UserTO> generateUsersTOsFrom(List<UserDO> users) {
        List<UserTO> userTOList = new ArrayList<UserTO>();
       for (UserDO userDO : users) {
           userTOList.add(generateUserTOFrom(userDO));
       }

       return userTOList;
    }

    public UserTO generateUserTOFrom(UserDO user) {
        UserTO userTO = new UserTO();
        userTO.setId(user.getId());
        userTO.setUsername(user.getUsername());
        userTO.setEmail(user.getEmail());
        userTO.setPersonalLibrary(generatePersonalLibraryTOFrom(user.getPersonalLibrary()));

        return userTO;
    }

    public PersonalLibraryTO generatePersonalLibraryTOFrom(PersonalLibraryDO personalLibraryDO) {
        PersonalLibraryTO personalLibraryTO = new PersonalLibraryTO();
        personalLibraryTO.setLikedAlbums(generateAlbumTOListFrom(personalLibraryDO.getLikedAlbums()));
        personalLibraryTO.setLikedArtists(generateArtistTOListFrom(personalLibraryDO.getLikedArtists()));
        personalLibraryTO.setLikedTracks(generateTrackTOListFrom(personalLibraryDO.getPlaylists().get(0).getTracks()));
        personalLibraryTO.setCustomPlaylists(generatePlaylistTOListFrom(personalLibraryDO.getPlaylists()));

        return personalLibraryTO;
    }

    private List<AlbumTO> generateAlbumTOListFrom(List<AlbumDO> albumDOList) {
        List<AlbumTO> albumTOList = new ArrayList<>();
        for (AlbumDO albumDO : albumDOList) {
            albumTOList.add(generateAlbumTOFrom(albumDO));
        }

        return albumTOList;
    }

    private  List<ArtistTO> generateArtistTOListFrom(List<ArtistDO> artistDOList) {
        List<ArtistTO> artistTOList = new ArrayList<>();
        for (ArtistDO artistDO : artistDOList) {
            artistTOList.add(generateArtistTOFrom(artistDO));
        }

        return artistTOList;
    }

    public  List<TrackTO> generateTrackTOListFrom(List<TrackDO> trackDOList) {
        List<TrackTO> trackTOList = new ArrayList<>();
        for (TrackDO trackDO : trackDOList) {
            trackTOList.add(generateTrackTOFrom(trackDO));
        }

        return trackTOList;
    }

    private  List<PlaylistTO> generatePlaylistTOListFrom(List<PlaylistDO> playlistDOList) {
        List<PlaylistTO> playlistTOList = new ArrayList<>();
        for (PlaylistDO playlistDO : playlistDOList) {
            playlistTOList.add(generatePlaylistTOFrom(playlistDO));
        }

        return playlistTOList;
    }

    public  AlbumTO generateAlbumTOFrom(AlbumDO albumDO) {
        AlbumTO albumTO = new AlbumTO();
        albumTO.setId(albumDO.getAlbum_id_deezer());
        albumTO.setTitle(albumDO.getTitle());
        albumTO.setCover_small(albumDO.getCover_small());
        albumTO.setCover_big(albumDO.getCover_big());
        albumTO.setRelease_date(albumDO.getRelease_date());
        albumTO.setNumber_of_songs(albumDO.getNumber_of_songs());

        return albumTO;
    }

    public  ArtistTO generateArtistTOFrom(ArtistDO artistDO) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(artistDO.getArtist_id_deezer());
        //artistTO.setDeezerId(artistDO.getArtist_id_deezer());
        artistTO.setNb_fans(artistDO.getNb_fans());
        artistTO.setName(artistDO.getName());
        artistTO.setPicture_small(artistDO.getPicture_small());
        artistTO.setPicture_big(artistDO.getPicture_big());

        return artistTO;
    }

    public  PlaylistTO generatePlaylistTOFrom(PlaylistDO playlistDO) {
        PlaylistTO playlistTO = new PlaylistTO();
        playlistTO.setId(playlistDO.getId());
        playlistTO.setTitle(playlistDO.getTitle());
        playlistTO.setPicture_small(playlistDO.getPicture_small());
        playlistTO.setPicture_big(playlistDO.getPicture_big());
        playlistTO.setNumber_of_songs(playlistDO.getNumber_of_songs());
        playlistTO.setTrackTOList(generateTrackTOListFrom(playlistDO.getTracks()));

        return playlistTO;
    }

    public  TrackTO generateTrackTOFrom(TrackDO trackDO) {
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
}
