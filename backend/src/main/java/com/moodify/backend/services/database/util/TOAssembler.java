package com.moodify.backend.services.database.util;

import com.moodify.backend.api.transferobjects.*;
import com.moodify.backend.api.util.Source;
import com.moodify.backend.services.database.objects.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to convert database objects (DO) to transfer objects (TO).
 * It contains methods for converting various types of DOs to TOs.
 * The class is annotated with @Component to indicate that it is an autodetect bean for Spring.
 */
@Component
public class TOAssembler {

    /**
     * This method is used to generate a UserTO object from a UserDO object.
     * It sets the id, username, email, and personal library of the UserTO object.
     * If the UserDO object has a discography, it also sets the discography of the UserTO object.
     *
     * @param user The UserDO object to be converted.
     * @return UserTO The generated UserTO object.
     */
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

    /**
     * This method is used to generate a PersonalLibraryTO object from a PersonalLibraryDO object.
     * It sets the liked albums, liked artists, liked tracks, and custom playlists of the PersonalLibraryTO object.
     *
     * @param personalLibraryDO The PersonalLibraryDO object to be converted.
     * @return PersonalLibraryTO The generated PersonalLibraryTO object.
     */
    public PersonalLibraryTO generatePersonalLibraryTOFrom(PersonalLibraryDO personalLibraryDO) {
        PersonalLibraryTO personalLibraryTO = new PersonalLibraryTO();
        personalLibraryTO.setLikedAlbums(generateAlbumTOListFrom(personalLibraryDO.getLikedAlbums()));
        personalLibraryTO.setLikedArtists(generateArtistTOListFrom(personalLibraryDO.getLikedArtists()));

        personalLibraryTO.setLikedTracks(generateTrackTOListFromTrackDOList(personalLibraryDO.getLikedTracksPlaylist().getTracks()));
        personalLibraryTO.setCustomPlaylists(generatePlaylistTOListFrom(personalLibraryDO.getCustomPlaylists()));

        return personalLibraryTO;
    }

    /**
     * This method is used to generate a list of AlbumTO objects from a list of AlbumDO objects.
     *
     * @param albumDOList The list of AlbumDO objects to be converted.
     * @return List<AlbumTO> The generated list of AlbumTO objects.
     */
    private List<AlbumTO> generateAlbumTOListFrom(List<AlbumDO> albumDOList) {
        List<AlbumTO> albumTOList = new ArrayList<>();
        for (AlbumDO albumDO : albumDOList) {
            albumTOList.add(generateAlbumTOFrom(albumDO));
        }

        return albumTOList;
    }

    /**
     * This method is used to generate a list of ArtistTO objects from a list of ArtistDO objects.
     *
     * @param artistDOList The list of ArtistDO objects to be converted.
     * @return List<ArtistTO> The generated list of ArtistTO objects.
     */
    public   List<ArtistTO> generateArtistTOListFrom(List<ArtistDO> artistDOList) {
        List<ArtistTO> artistTOList = new ArrayList<>();
        for (ArtistDO artistDO : artistDOList) {
            artistTOList.add(generateArtistTOFrom(artistDO));
        }

        return artistTOList;
    }

    /**
     * This method is used to generate a list of TrackTO objects from a list of TrackDO objects.
     *
     * @param trackDOList The list of TrackDO objects to be converted.
     * @return List<TrackTO> The generated list of TrackTO objects.
     */
    public  List<TrackTO> generateTrackTOListFromTrackDOList(List<TrackDO> trackDOList) {
        List<TrackTO> trackTOList = new ArrayList<>();
        for (TrackDO trackDO : trackDOList) {
            trackTOList.add(generateTrackTOFrom(trackDO));
        }

        return trackTOList;
    }

    /**
     * This method is used to generate a list of PlaylistTO objects from a list of PlaylistDO objects.
     *
     * @param playlistDOList The list of PlaylistDO objects to be converted.
     * @return List<PlaylistTO> The generated list of PlaylistTO objects.
     */
    public   List<PlaylistTO> generatePlaylistTOListFrom(List<PlaylistDO> playlistDOList) {
        List<PlaylistTO> playlistTOList = new ArrayList<>();
        for (PlaylistDO playlistDO : playlistDOList) {
            playlistTOList.add(generatePlaylistTOFrom(playlistDO));
        }

        return playlistTOList;
    }

    /**
     * This method is used to generate an AlbumTO object from an AlbumDO object.
     * It sets the id, title, small cover, big cover, release date, and number of songs of the AlbumTO object.
     *
     * @param albumDO The AlbumDO object to be converted.
     * @return AlbumTO The generated AlbumTO object.
     */
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

    /**
     * This method is used to generate an ArtistTO object from an ArtistDO object.
     * It sets the id, number of fans, name, small picture, and big picture of the ArtistTO object.
     *
     * @param artistDO The ArtistDO object to be converted.
     * @return ArtistTO The generated ArtistTO object.
     */
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

    /**
     * This method is used to generate a PlaylistTO object from a PlaylistDO object.
     * It sets the id, title, small picture, big picture, number of songs, and track list of the PlaylistTO object.
     *
     * @param playlistDO The PlaylistDO object to be converted.
     * @return PlaylistTO The generated PlaylistTO object.
     */
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

    /**
     * This method is used to generate a TrackTO object from a TrackDO object.
     * It sets the id, title, duration, preview, release date, artist id, artist name, big album cover, and small album cover of the TrackTO object.
     *
     * @param trackDO The TrackDO object to be converted.
     * @return TrackTO The generated TrackTO object.
     */
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

    /**
     * This method is used to generate a DiscographyTO object from a DiscographyDO object.
     * It sets the big picture, small picture, and singles of the DiscographyTO object.
     *
     * @param discographyDO The DiscographyDO object to be converted.
     * @return DiscographyTO The generated DiscographyTO object.
     */
    public  DiscographyTO generateDiscographyTO(DiscographyDO discographyDO) {
        DiscographyTO discography = new DiscographyTO();
        discography.setPicture_big(discographyDO.getPicture_big());
        discography.setPicture_small(discographyDO.getPicture_small());

        for (MoodifySingleDO single : discographyDO.getSingles()) {
            discography.getSingles().add(this.generateTrackTOFrom(single));
        }

        return discography;
    }

    /**
     * This method is used to generate a TrackTO object from a MoodifySingleDO object.
     * It sets the id, title, preview, release date, duration, big album cover, small album cover, source, and artist of the TrackTO object.
     *
     * @param single The MoodifySingleDO object to be converted.
     * @return TrackTO The generated TrackTO object.
     */
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

    /**
     * This method is used to generate a list of TrackTO objects from a list of MoodifySingleDO objects.
     * It iterates over the list of MoodifySingleDO objects and for each object, it generates a TrackTO object using the generateTrackTOFrom method.
     *
     * @param singles The list of MoodifySingleDO objects to be converted.
     * @return List<TrackTO> The generated list of TrackTO objects.
     */
    public  List<TrackTO> generateTrackTOListFromMoodifySingleDOList(List<MoodifySingleDO> singles) {
        List<TrackTO> trackTOList = new ArrayList<>();
        for (MoodifySingleDO single : singles) {
            trackTOList.add(generateTrackTOFrom(single));
        }

        return trackTOList;
    }

    /**
     * This method is used to generate a list of ArtistTO objects from a list of UserDO objects.
     * It iterates over the list of UserDO objects and for each object, it generates an ArtistTO object and sets its properties.
     *
     * @param userDOS The list of UserDO objects to be converted.
     * @return List<ArtistTO> The generated list of ArtistTO objects.
     */
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

    /**
     * This method is used to generate an ArtistTO object from a UserDO object.
     * It creates an ArtistTO object and sets its properties.
     *
     * @param moodifyArtist The UserDO object to be converted.
     * @return ArtistTO The generated ArtistTO object.
     */
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
