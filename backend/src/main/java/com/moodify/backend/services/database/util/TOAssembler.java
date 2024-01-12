package com.moodify.backend.services.database.util;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.api.util.Source;
import com.moodify.backend.services.database.objects.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TOAssembler {

    public UserTO generateUserTOFrom(UserDO user) {
        UserTO userTO = new UserTO();
        userTO.setId(user.getId());
        userTO.setUsername(user.getUsername());
        userTO.setEmail(user.getEmail());
        userTO.setPersonalLibrary(generatePersonalLibraryTOFrom(user.getPersonalLibrary()));
        if (user.getDiscography() != null) {
            userTO.setDiscography(this.generateDiscographyTO(user.getDiscography()));
        }
        return userTO;
    }

    public PersonalLibraryTO generatePersonalLibraryTOFrom(PersonalLibraryDO personalLibraryDO) {
        PersonalLibraryTO personalLibraryTO = new PersonalLibraryTO();
        personalLibraryTO.setLikedAlbums(generateAlbumTOListFrom(personalLibraryDO.getLikedAlbums()));
        personalLibraryTO.setLikedArtists(generateArtistTOListFrom(personalLibraryDO.getLikedArtists()));

        personalLibraryTO.setLikedTracks(generateTrackTOListFromTrackDOList(personalLibraryDO.getLikedTracksPlaylist().getTracks()));
        personalLibraryTO.setCustomPlaylists(generatePlaylistTOListFrom(personalLibraryDO.getCustomPlaylists()));

        return personalLibraryTO;
    }

    private List<AlbumTO> generateAlbumTOListFrom(List<AlbumDO> albumDOList) {
        List<AlbumTO> albumTOList = new ArrayList<>();
        for (AlbumDO albumDO : albumDOList) {
            albumTOList.add(generateAlbumTOFrom(albumDO));
        }

        return albumTOList;
    }

    public   List<ArtistTO> generateArtistTOListFrom(List<ArtistDO> artistDOList) {
        List<ArtistTO> artistTOList = new ArrayList<>();
        for (ArtistDO artistDO : artistDOList) {
            artistTOList.add(generateArtistTOFrom(artistDO));
        }

        return artistTOList;
    }

    public  List<TrackTO> generateTrackTOListFromTrackDOList(List<TrackDO> trackDOList) {
        List<TrackTO> trackTOList = new ArrayList<>();
        for (TrackDO trackDO : trackDOList) {
            trackTOList.add(generateTrackTOFrom(trackDO));
        }

        return trackTOList;
    }

    public   List<PlaylistTO> generatePlaylistTOListFrom(List<PlaylistDO> playlistDOList) {
        List<PlaylistTO> playlistTOList = new ArrayList<>();
        for (PlaylistDO playlistDO : playlistDOList) {
            playlistTOList.add(generatePlaylistTOFrom(playlistDO));
        }

        return playlistTOList;
    }

    public  AlbumTO generateAlbumTOFrom(AlbumDO albumDO) {
        AlbumTO albumTO = new AlbumTO();
        albumTO.setId(albumDO.getAlbum_id_source());
        albumTO.setTitle(albumDO.getTitle());
        albumTO.setCover_small(albumDO.getCover_small());
        albumTO.setCover_big(albumDO.getCover_big());
        albumTO.setRelease_date(albumDO.getRelease_date());
        albumTO.setNumber_of_songs(albumDO.getNumber_of_songs());

        return albumTO;
    }

    public  ArtistTO generateArtistTOFrom(ArtistDO artistDO) {
        ArtistTO artistTO = new ArtistTO();
        artistTO.setId(artistDO.getArtist_id_source());
        //artistTO.setDeezerId(artistDO.getArtist_id_source());
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
        playlistTO.setTrackTOList(this.generateTrackTOListFromTrackDOList(playlistDO.getTracks()));

        return playlistTO;
    }

    public  TrackTO generateTrackTOFrom(TrackDO trackDO) {
        TrackTO trackTO =  new TrackTO();
        trackTO.setId(trackDO.getId_source());
        trackTO.setTitle(trackDO.getTitle());
        trackTO.setDuration(trackDO.getDuration());
        trackTO.setPreview(trackDO.getPreview());
        trackTO.setRelease_date(trackDO.getRelease_date());
        trackTO.getArtist().setId(trackDO.getArtist_id_source());
        trackTO.getArtist().setName(trackDO.getArtist_name());
        trackTO.getAlbum().setCover_big(trackDO.getCover_big());
        trackTO.getAlbum().setCover_small(trackDO.getCover_small());

        return trackTO;
    }

    public  DiscographyTO generateDiscographyTO(DiscographyDO discographyDO) {
        DiscographyTO discography = new DiscographyTO();
        discography.setPicture_big(discographyDO.getPicture_big());
        discography.setPicture_small(discographyDO.getPicture_small());

        for (MoodifySingleDO single : discographyDO.getSingles()) {
            discography.getSingles().add(this.generateTrackTOFrom(single));
        }

        return discography;
    }

    public TrackTO generateTrackTOFrom(MoodifySingleDO single) {

        ArtistTO artist = new ArtistTO();
        artist.setId(single.getArtist_id());
        artist.setName(single.getArtist_name());
        artist.setSource(Source.MOODIFY);

        TrackTO trackSingle = new TrackTO();
        trackSingle.setId(single.getId());
        trackSingle.setTitle(single.getTitle());
        trackSingle.setPreview(single.getPreview());
        trackSingle.setRelease_date(single.getRelease_date());
        trackSingle.setDuration(single.getDuration());
        trackSingle.getAlbum().setCover_big(single.getCover_big());
        trackSingle.getAlbum().setCover_small(single.getCover_small());
        trackSingle.setSource(Source.MOODIFY);


        trackSingle.setArtist(artist);



        return trackSingle;
    }

    public  List<TrackTO> generateTrackTOListFromMoodifySingleDOList(List<MoodifySingleDO> singles) {
        List<TrackTO> trackTOList = new ArrayList<>();
        for (MoodifySingleDO single : singles) {
            trackTOList.add(generateTrackTOFrom(single));
        }

        return trackTOList;
    }

    public List<ArtistTO> generateArtistTOFromUserDO(List<UserDO> userDOS) {
        List<ArtistTO> artists = new ArrayList<ArtistTO>();
        for (UserDO moodifyArtist : userDOS) {
            ArtistTO artist = new ArtistTO();
            artist.setName(moodifyArtist.getUsername());
            artist.setId(moodifyArtist.getId());

            artist.setTrackTOList(this.generateTrackTOListFromMoodifySingleDOList(moodifyArtist.getDiscography().getSingles()));
            artist.setPicture_small(moodifyArtist.getDiscography().getPicture_small());
            artist.setPicture_big(moodifyArtist.getDiscography().getPicture_big());

            artists.add(artist);
        }

        return artists;
    }

    public ArtistTO generateArtistTOFromMoodifyArtist(UserDO moodifyArtist) {
        ArtistTO artist = new ArtistTO();
        artist.setId(moodifyArtist.getId());
        artist.setName(moodifyArtist.getUsername());
        artist.setPicture_small(moodifyArtist.getDiscography().getPicture_small());
        artist.setPicture_big(moodifyArtist.getDiscography().getPicture_big());
        artist.setTrackTOList(this.generateTrackTOListFromMoodifySingleDOList(moodifyArtist.getDiscography().getSingles()));

        return artist;
    }
}
